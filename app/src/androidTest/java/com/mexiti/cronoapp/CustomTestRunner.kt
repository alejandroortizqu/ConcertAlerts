package com.mexiti.cronoapp

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

// Este es nuestro corredor de pruebas personalizado
class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        // Forzamos a que las pruebas usen la aplicación de Hilt para testing
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}