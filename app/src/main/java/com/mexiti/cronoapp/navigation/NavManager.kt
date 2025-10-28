package com.mexiti.cronoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mexiti.cronoapp.ui.views.AddView
import com.mexiti.cronoapp.ui.views.EditView
import com.mexiti.cronoapp.ui.views.HomeView
import com.mexiti.cronoapp.ui.views.ProfileView
import com.mexiti.cronoapp.viewmodel.AppDataViewModel
import com.mexiti.cronoapp.viewmodel.CronometroViewModel

@Composable
fun NavManager(cronometroVM: CronometroViewModel, dataVM: AppDataViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Home") {

        composable("Home") {
            HomeView(navController, dataVM)
        }

        composable("AddView") {
            AddView(navController, cronometroVM, dataVM)
        }

        composable(
            "EditView/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            EditView(
                navController = navController,
                cronometroVM = cronometroVM,
                dataVM = dataVM,
                id = id
            )
        }

        // Perfil
        composable("ProfileView") {
            ProfileView(
                dataVM = dataVM,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
