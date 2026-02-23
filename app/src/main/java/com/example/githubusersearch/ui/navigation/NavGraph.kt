package com.example.githubusersearch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.githubusersearch.ui.screens.ProfileScreen
import com.example.githubusersearch.ui.screens.SearchScreen
import com.example.githubusersearch.viewmodel.UserViewModel

@Composable
fun NavGraph() {

    val navController = rememberNavController()
    val viewModel: UserViewModel = viewModel()

    NavHost(navController = navController, startDestination = "search") {

        composable("search") {
            SearchScreen(navController)
        }

        composable("profile/{username}") { backStackEntry ->

            val username = backStackEntry.arguments?.getString("username") ?: ""
            val context = LocalContext.current

            ProfileScreen(username, viewModel, navController, context)
        }
    }
}