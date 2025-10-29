package com.mexiti.cronoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.mexiti.cronoapp.navigation.NavManager
import com.mexiti.cronoapp.ui.theme.AppTheme
import com.mexiti.cronoapp.viewmodel.AppDataViewModel
import com.mexiti.cronoapp.viewmodel.CronometroViewModel
import dagger.hilt.android.AndroidEntryPoint


/*@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cronometroVM:CronometroViewModel by viewModels()
        val dataVM: AppDataViewModel by viewModels()
        setContent {
            AppTheme {
                NavManager(cronometroVM, dataVM)
            }
        }
    }
}*/


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ❌ Eliminamos la declaración del ViewModel que ya no usamos
        // val cronometroVM:CronometroViewModel by viewModels()

        // ✅ Solo mantenemos el ViewModel necesario
        val dataVM: AppDataViewModel by viewModels()

        setContent {
            AppTheme {
                // ❌ Eliminamos el argumento 'cronometroVM' de la llamada
                NavManager(dataVM) // 👈 ¡CORRECCIÓN AQUÍ!
            }
        }
    }
}

