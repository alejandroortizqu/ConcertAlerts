// navigation/NavManager.kt

package com.mexiti.cronoapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mexiti.cronoapp.ui.views.CalculatorView
import com.mexiti.cronoapp.ui.views.HomeView
import com.mexiti.cronoapp.ui.views.ProfileView
import com.mexiti.cronoapp.viewmodel.CalculatorViewModel
import com.mexiti.cronoapp.viewmodel.ConcertViewModel

@Composable
fun NavManager(navController: NavHostController) {

    // Inyección de ViewModels con Hilt
    val concertVM: ConcertViewModel = hiltViewModel()
    val calculatorVM: CalculatorViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "Home") {

        composable("Home") {
            // ✅ HomeView espera 'dataVM: ConcertViewModel'
            HomeView(navController = navController, dataVM = concertVM)
        }

        composable("CalculatorView") {
            // ✅ CalculatorView espera 'dataVM: CalculatorViewModel'
            CalculatorView(navController = navController, dataVM = calculatorVM)
        }

        composable("ProfileView") {
            // ✅ ProfileView espera 'dataVM: ConcertViewModel'
            ProfileView(
                dataVM = concertVM,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

/*package com.mexiti.cronoapp.navigation
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

*/









/*package com.mexiti.cronoapp.navigation
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
*/