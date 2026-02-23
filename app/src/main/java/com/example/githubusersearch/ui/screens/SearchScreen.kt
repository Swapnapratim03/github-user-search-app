package com.example.githubusersearch.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.githubusersearch.data.repository.SearchHistoryManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalFocusManager


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {

    val context = LocalContext.current
    val historyManager = remember { SearchHistoryManager(context) }

    var username by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    // load history
    var history by remember { mutableStateOf(historyManager.getHistory()) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("GitHub User Search") })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ExposedDropdownMenuBox(
                expanded = expanded && history.isNotEmpty(),
                onExpandedChange = { expanded = !expanded }
            ) {

                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it
                        error = false
                    },
                    label = { Text("GitHub Username") },
                    isError = error,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .onFocusChanged { expanded = it.isFocused },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null)
                    },
                    singleLine = true
                )

                ExposedDropdownMenu(
                    expanded = expanded && history.isNotEmpty(),
                    onDismissRequest = { expanded = false }
                ) {

                    history.take(5).forEach { item ->

                        DropdownMenuItem(
                            text = {
                                Row {
                                    Icon(Icons.Default.History, contentDescription = null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(item)
                                }
                            },
                            onClick = {
                                username = item
                                expanded = false
                            }
                        )
                    }
                }
            }

            if (error) {
                Text(
                    text = "Username cannot be empty",
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(20.dp))


            val keyboardController = LocalSoftwareKeyboardController.current
            val focusManager = LocalFocusManager.current

            Button(
                onClick = {

                    if (username.isBlank()) {
                        error = true
                        return@Button
                    }

                    //  close keyboard first
                    focusManager.clearFocus()
                    keyboardController?.hide()

                    // navigate
                    navController.navigate("profile/$username")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search")
            }
        }
    }
}