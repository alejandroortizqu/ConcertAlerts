package com.mexiti.cronoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.mexiti.cronoapp.navigation.NavManager
import com.mexiti.cronoapp.ui.theme.AppTheme
import com.mexiti.cronoapp.viewmodel.AppDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // mantenemos el ViewModel necesario
        val dataVM: AppDataViewModel by viewModels()

        //Esta es una prueba

        //Esta es una prueba

        setContent {
            AppTheme {

                NavManager(dataVM) //
            }
        }
    }
}

