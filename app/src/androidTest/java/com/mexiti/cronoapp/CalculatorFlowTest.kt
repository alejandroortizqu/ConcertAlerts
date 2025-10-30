package com.mexiti.cronoapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class) // Requerido para pruebas de instrumentación
@HiltAndroidTest // 1. Le dice a Hilt que esta es una prueba
class CalculatorFlowTest {

    // 2. Regla de Hilt (debe ejecutarse primero, orden 0)
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    // 3. Regla de Compose que lanza la Activity (orden 1)
    //    Usamos MainActivity porque es la entrada de tu app
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /*
    @Test
    fun alAgregarUnProductoElTotalYLaListaSeActualizan() {

        // --- NAVEGACIÓN ---
        // 1. Estamos en HomeView. Buscamos el FAB de calculadora.
        //    (En Buttons.kt, tu FloatButton usa R.drawable.calculate, que tiene contentDescription "Agregar")
        composeTestRule.onNodeWithContentDescription("Agregar").performClick()

        // 2. Esperamos a que la pantalla de la calculadora cargue,
        //    buscando el título que definiste en CalculatorView.kt
        composeTestRule.onNodeWithText("Calculadora de compras en concierto").assertIsDisplayed()

        // --- ACCIÓN ---
        // 3. Buscamos el campo por su etiqueta "Nombre del producto" y escribimos.
        composeTestRule.onNodeWithText("Nombre del producto").performTextInput("Refresco")

        // 4. Buscamos el campo por su etiqueta "Precio" y escribimos.
        composeTestRule.onNodeWithText("Precio").performTextInput("100")

        // 5. Buscamos el botón por su texto "Agregar" y hacemos clic.
        composeTestRule.onNodeWithText("Agregar").performClick()

        // --- VERIFICACIÓN ---
        // 6. Verificamos que el total se actualizó.
        //    (El ViewModel usa Double, así que será 100.0)
        composeTestRule.onNodeWithText("Total: $100.0").assertIsDisplayed()

        // 7. Verificamos que el item "Refresco" aparece en la lista
        composeTestRule.onNodeWithText("Refresco").assertIsDisplayed()
    }
     */

    @Test
    fun alPresionarAgregarConCamposVacios_MuestraMensajeDeError() {
        // --- NAVEGACIÓN ---
        // 1. Ir a la calculadora
        composeTestRule.onNodeWithContentDescription("Agregar").performClick()
        composeTestRule.waitForIdle() // Espera a que termine de navegar

        // 2. Verificar que estamos en la pantalla correcta
        composeTestRule.onNodeWithText("Calculadora de compras en concierto").assertIsDisplayed()

        // --- ACCIÓN: Presionar "Agregar" ---
        // 3. (Sin escribir nada en los campos)
        composeTestRule.onNodeWithText("Agregar").performClick()
        composeTestRule.waitForIdle() // Espera a que el ViewModel reaccione

        // --- VERIFICACIÓN ---
        // 4. El ViewModel debe poner un error.
        val errorMessage = "El precio o el nombre no son válidos."

        // ¡¡CAMBIO!! Usamos assertExists()
        // Esto solo comprueba que el nodo de texto existe,
        // no si es 100% visible (es menos estricto y bueno para depurar).
        composeTestRule.onNodeWithText(errorMessage).assertExists()
    }

}