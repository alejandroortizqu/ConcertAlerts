package com.mexiti.cronoapp.viewmodel

import com.mexiti.cronoapp.model.Gasto
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CalculatorViewModelTest {

    // 1. Preparamos el ViewModel que vamos a probar
    private lateinit var viewModel: CalculatorViewModel

    // 2. La anotación @Before asegura que esta función
    //    se ejecute antes de CADA prueba.
    //    Esto nos da un ViewModel "limpio" para cada caso.
    @Before
    fun setUp() {
        viewModel = CalculatorViewModel()
    }

    // 3. Nombramos la prueba de forma descriptiva
    @Test
    fun addProductConNombreYPrecioVálidosDebeAgregarProductoYLimpiarCampos() {
        // Arrange (Preparar)
        viewModel.onNameChange("Refresco")
        viewModel.onPriceChange("100.0")

        // Act (Actuar)
        viewModel.addProduct()

        // Assert (Verificar)
        val state = viewModel.uiState.value
        val expectedProduct = Gasto(id = 1, nombre = "Refresco", precio = 100.0)

        assertEquals(1, state.productList.size) // Verifica que la lista tiene 1 producto
        assertEquals(expectedProduct, state.productList.first()) // Verifica que es el producto correcto
        assertEquals("", state.newProductName) // Verifica que el campo de nombre se limpió
        assertEquals("", state.newProductPrice) // Verifica que el campo de precio se limpió
        assertNull(state.errorMessage) // Verifica que no hay mensaje de error
    }

    @Test
    fun addProductConNombreVacíoDebeMostrarErrorYNoAgregarProducto() {
        // Arrange (Preparar)
        viewModel.onNameChange("")
        viewModel.onPriceChange("100.0")

        // Act (Actuar)
        viewModel.addProduct()

        // Assert (Verificar)
        val state = viewModel.uiState.value

        assertEquals(0, state.productList.size) // La lista debe estar vacía
        assertEquals("El precio o el nombre no son válidos.", state.errorMessage) // Verifica el mensaje de error
    }

    @Test
    fun addProductConPrecioInvalidoDebeMostrarErrorYNoAgregarProducto() {
        // Arrange (Preparar)
        viewModel.onNameChange("Refresco")
        viewModel.onPriceChange("abc") // Precio no numérico

        // Act (Actuar)
        viewModel.addProduct()

        // Assert (Verificar)
        val state = viewModel.uiState.value

        assertEquals(0, state.productList.size) // La lista debe estar vacía
        assertEquals("El precio o el nombre no son válidos.", state.errorMessage) // Verifica el mensaje de error
    }

    @Test
    fun removeProductDebeQuitarElProductoDeLaLista() {
        // Arrange (Preparar) - Añadimos dos productos primero
        viewModel.onNameChange("Refresco")
        viewModel.onPriceChange("100.0")
        viewModel.addProduct() // Producto con id = 1

        viewModel.onNameChange("Cerveza")
        viewModel.onPriceChange("200.0")
        viewModel.addProduct() // Producto con id = 2

        assertEquals(2, viewModel.uiState.value.productList.size) // Verificamos que hay 2

        // Act (Actuar) - Eliminamos el primer producto (id = 1)
        viewModel.removeProduct(1)

        // Assert (Verificar)
        val state = viewModel.uiState.value
        val expectedProduct = Gasto(id = 2, nombre = "Cerveza", precio = 200.0)

        assertEquals(1, state.productList.size) // La lista ahora debe tener 1
        assertEquals(expectedProduct, state.productList.first()) // El producto restante debe ser "Cerveza"
    }

    @Test
    fun addProductAlAgregarDosProductosLosIdsSonIncrementalesYLosCamposSeLimpian() {
        // --- Primer producto ---
        // Arrange
        viewModel.onNameChange("Refresco")
        viewModel.onPriceChange("100")

        // Act
        viewModel.addProduct()

        // Assert (Verificar estado intermedio)
        val state1 = viewModel.uiState.value
        assertEquals("El id del primer producto debe ser 1", 1, state1.productList.first().id)
        assertEquals("El campo de nombre debió limpiarse", "", state1.newProductName)

        // --- Segundo producto ---
        // Arrange
        viewModel.onNameChange("Cerveza")
        viewModel.onPriceChange("200")

        // Act
        viewModel.addProduct()

        // Assert (Verificar estado final)
        val state2 = viewModel.uiState.value
        assertEquals("La lista debe tener 2 productos", 2, state2.productList.size)
        // Buscamos el último producto añadido (el segundo)
        assertEquals("El id del segundo producto debe ser 2", 2, state2.productList.last().id)
        assertEquals("El nombre debe ser Cerveza", "Cerveza", state2.productList.last().nombre)
        assertEquals("El campo de nombre debió limpiarse de nuevo", "", state2.newProductName)
        assertEquals("El campo de precio debió limpiarse de nuevo", "", state2.newProductPrice)
    }
}