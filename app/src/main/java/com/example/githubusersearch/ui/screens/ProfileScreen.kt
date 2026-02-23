package com.example.githubusersearch.ui.screens
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.githubusersearch.viewmodel.UserViewModel
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    username: String,
    viewModel: UserViewModel,
    navController: NavController,
    context: Context
) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchUser(username, context)
    }

    val user = viewModel.user

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            if (viewModel.isLoading) {
                CircularProgressIndicator()
            } else {

                user?.let {

                    Card(
                        modifier = Modifier
                            .padding(24.dp)
                            .widthIn(max = 360.dp),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            AsyncImage(
                                model = it.avatar_url,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(140.dp)
                                    .clip(CircleShape)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = it.name ?: "No Name",
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = it.bio ?: "No Bio Available",
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "@${it.login}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("${it.followers}", style = MaterialTheme.typography.titleMedium)
                                    Text("Followers", style = MaterialTheme.typography.bodySmall)
                                }

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("${it.following}", style = MaterialTheme.typography.titleMedium)
                                    Text("Following", style = MaterialTheme.typography.bodySmall)
                                }

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("${it.public_repos}", style = MaterialTheme.typography.titleMedium)
                                    Text("Repos", style = MaterialTheme.typography.bodySmall)
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                } ?: Text("User not found")
            }
        }
    }
}