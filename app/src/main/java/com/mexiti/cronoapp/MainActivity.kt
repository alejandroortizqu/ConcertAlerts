// MainActivity.kt (Código Corregido)

package com.mexiti.cronoapp

import androidx.activity.viewModels
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController // 🟢 Nueva importación necesaria
import com.mexiti.cronoapp.navigation.NavManager
import com.mexiti.cronoapp.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.mexiti.cronoapp.viewmodel.CalculatorViewModel
import com.mexiti.cronoapp.viewmodel.ConcertViewModel
 // Para inyección en NavHost

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ❌ ELIMINAR O COMENTAR: Los VMs ya no se declaran aquí.
        // Se inyectan dentro del Composable NavManager o sus rutas.
        // val calculatorVM: CalculatorViewModel by viewModels()
        // val concertVM: ConcertViewModel by viewModels()

        setContent {
            AppTheme {
                // 🟢 CREAR el NavController
                val navController = rememberNavController()

                // 🟢 LLAMADA CORREGIDA: Pasar solo el NavHostController
                NavManager(navController)
            }
        }
    }
}



/*package com.mexiti.cronoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.mexiti.cronoapp.navigation.NavManager
import com.mexiti.cronoapp.ui.theme.AppTheme
import com.mexiti.cronoapp.viewmodel.ConcertViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // mantenemos el ViewModel necesario
        val dataVM: ConcertViewModel by viewModels()

        //Esta es una prueba

        //Esta es una prueba

        setContent {
            AppTheme {

                NavManager(dataVM) //
            }
        }
    }
}
*/
