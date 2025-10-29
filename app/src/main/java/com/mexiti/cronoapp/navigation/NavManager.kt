/*package com.mexiti.cronoapp.navigation

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
}*/




/*package com.mexiti.cronoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
// Importación de las Vistas restantes:
import com.mexiti.cronoapp.ui.views.CalculatorView // La vista de tu calculadora
import com.mexiti.cronoapp.ui.views.HomeView
import com.mexiti.cronoapp.ui.views.ProfileView
// Importación del único ViewModel necesario:
import com.mexiti.cronoapp.viewmodel.AppDataViewModel
// Quitamos importaciones de NavType y navArgument (ya no se usan)
// Quitamos la importación de EditView (ya no se usa)
// Quitamos la importación de CronometroViewModel (ya no se usa)


@Composable
fun NavManager(dataVM: AppDataViewModel) { // 👈 Solo se pasa AppDataViewModel
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "CalculatorView") { // 👈 Iniciamos en la Calculadora

        // 🟢 RUTA PRINCIPAL: La Calculadora de Compras
        composable("CalculatorView") {
            // Pasamos solo navController y dataVM.
            // Si CalculatorView no usa dataVM, podrías eliminarlo de los parámetros.
            CalculatorView(navController = navController, dataVM = dataVM)
        }

        // Home View (si ahora HomeView muestra listas de conciertos o algo más)
        composable("Home") {
            HomeView(navController, dataVM)
        }

        // Perfil / Ajustes
        composable("ProfileView") {
            ProfileView(
                dataVM = dataVM,
                onBack = { navController.popBackStack() }
            )
        }

        // ❌ EditView/{id} fue eliminado por ser específico del cronómetro.
    }
}*/


package com.mexiti.cronoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
// Importación de las Vistas restantes:
import com.mexiti.cronoapp.ui.views.CalculatorView
import com.mexiti.cronoapp.ui.views.HomeView
import com.mexiti.cronoapp.ui.views.ProfileView
// Importación del único ViewModel necesario:
import com.mexiti.cronoapp.viewmodel.AppDataViewModel
// Eliminamos todas las importaciones no utilizadas (NavType, navArgument, EditView, CronometroViewModel)


/*@Composable
fun NavManager(dataVM: AppDataViewModel) { // 👈 Firma simplificada
    val navController = rememberNavController()

    // Iniciamos la navegación en la nueva vista de calculadora
    NavHost(navController = navController, startDestination = "CalculatorView") {

        // 🟢 RUTA PRINCIPAL: Calculadora de Compras
        composable("CalculatorView") {
            // Pasamos solo NavController y AppDataViewModel
            CalculatorView(navController = navController, dataVM = dataVM)
        }

        // Home View
        composable("Home") {
            // Aseguramos que HomeView solo reciba lo que necesita
            HomeView(navController, dataVM)
        }

        // Perfil / Ajustes
        composable("ProfileView") {
            ProfileView(
                dataVM = dataVM,
                onBack = { navController.popBackStack() }
            )
        }

        // La ruta "EditView/{id}" (que era la línea 84 del error) fue eliminada permanentemente.
    }
}
*/


// NavManager.kt

@Composable
fun NavManager(dataVM: AppDataViewModel) {
    val navController = rememberNavController()

    // 🔴 CORRECCIÓN CLAVE: Iniciar en "Home"
    NavHost(navController = navController, startDestination = "Home") {

        composable("Home") {
            HomeView(navController, dataVM)
        }

        composable("CalculatorView") {
            // Esto asume que CalculatorView acepta (navController, dataVM)
            CalculatorView(navController = navController, dataVM = dataVM)
        }

        // La ruta ProfileView se mantiene.
        composable("ProfileView") {
            ProfileView(
                dataVM = dataVM,
                onBack = { navController.popBackStack() }
            )
        }

        // La ruta EditView fue eliminada previamente.
    }
}