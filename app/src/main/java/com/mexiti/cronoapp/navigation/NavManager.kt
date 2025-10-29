package com.mexiti.cronoapp.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mexiti.cronoapp.ui.views.CalculatorView
import com.mexiti.cronoapp.ui.views.HomeView
import com.mexiti.cronoapp.ui.views.ProfileView
import com.mexiti.cronoapp.viewmodel.AppDataViewModel


@Composable
fun NavManager(dataVM: AppDataViewModel) {
    val navController = rememberNavController()

    // Iniciar en "Home"
    NavHost(navController = navController, startDestination = "Home") {

        composable("Home") {
            HomeView(navController, dataVM)
        }

        composable("CalculatorView") {
            // CalculatorView acepta (navController, dataVM)
            CalculatorView(navController = navController, dataVM = dataVM)
        }

        // La ruta ProfileView se mantiene.
        composable("ProfileView") {
            ProfileView(
                dataVM = dataVM,
                onBack = { navController.popBackStack() }
            )
        }

    }
}