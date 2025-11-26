// MainActivity.kt (Código Corregido)

package com.mexiti.cronoapp

import androidx.activity.viewModels
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.mexiti.cronoapp.navigation.NavManager
import com.mexiti.cronoapp.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.mexiti.cronoapp.viewmodel.CalculatorViewModel
import com.mexiti.cronoapp.viewmodel.ConcertViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            AppTheme {
                // CREAR el NavController
                val navController = rememberNavController()

                // Pasar solo el NavHostController
                NavManager(navController)
            }
        }
    }
}



