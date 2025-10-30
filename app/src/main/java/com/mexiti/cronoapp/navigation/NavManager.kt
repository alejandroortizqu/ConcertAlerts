package com.mexiti.cronoapp.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mexiti.cronoapp.ui.views.CalculatorView
import com.mexiti.cronoapp.ui.views.HomeView
import com.mexiti.cronoapp.ui.views.ProfileView
import com.mexiti.cronoapp.viewmodel.ConcertViewModel


@Composable
fun NavManager(dataVM: ConcertViewModel) {
    val navController = rememberNavController()

    // Iniciar en "Home"
    NavHost(navController = navController, startDestination = "Home") {

        composable("Home") {
            HomeView(navController, dataVM)
        }

        composable("CalculatorView") {
            // CalculatorView ahora solo necesita el NavController
            CalculatorView(navController = navController)
        }

        // La ruta ProfileView se mantiene.
        composable("ProfileView") {
            ProfileView(
                onBack = { navController.popBackStack() }
            )
        }

    }
}
