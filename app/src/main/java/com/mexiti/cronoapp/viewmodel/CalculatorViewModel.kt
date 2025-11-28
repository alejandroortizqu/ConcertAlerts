package com.mexiti.cronoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mexiti.cronoapp.model.ConciertoItem
import com.mexiti.cronoapp.repository.ConciertoRepository
import com.mexiti.cronoapp.state.CalculatorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val repository: ConciertoRepository
) : ViewModel() {

    // 1. ESTADO DE LA LISTA (Persistencia con ROOM)
    val productList: StateFlow<List<ConciertoItem>> = repository.getAllItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    // 2. ESTADO DE LOS INPUTS Y ERRORES (Usa la clase importada)
    private val _uiState = MutableStateFlow(CalculatorState())
    val uiState: StateFlow<CalculatorState> = _uiState.asStateFlow()

    // LÓGICA DE CAMBIO DE INPUTS
    fun onProductNameChange(name: String) {
        _uiState.update { it.copy(newProductName = name, errorMessage = null) }
    }

    fun onProductPriceChange(price: String) {
        val filteredPrice = price.filter { c -> c.isDigit() || (c == '.') }
        _uiState.update { it.copy(newProductPrice = filteredPrice, errorMessage = null) }
    }

    // 3. FUNCIÓN PARA AGREGAR PRODUCTO (ROOM)
    fun addProduct() {
        val newName = _uiState.value.newProductName
        val priceString = _uiState.value.newProductPrice
        val newPrice = priceString.toDoubleOrNull()

        // Validación
        if (newName.isBlank() || newPrice == null || newPrice <= 0.0) {
            _uiState.update { it.copy(errorMessage = "El precio o el nombre no son válidos.") }
            return
        }

        viewModelScope.launch {
            repository.insertItem(
                ConciertoItem(nombre = newName, precio = newPrice)
            )

            // Limpia los inputs después de agregar
            _uiState.update { it.copy(newProductName = "", newProductPrice = "", errorMessage = null) }
        }
    }

    // 4. FUNCIÓN PARA ELIMINAR PRODUCTO (ROOM)
    fun removeProduct(product: ConciertoItem) {
        viewModelScope.launch {
            repository.deleteItem(product)
        }
    }
}







/*

package com.mexiti.cronoapp.viewmodel




import androidx.lifecycle.ViewModel
import com.mexiti.cronoapp.model.ConciertoItem
import com.mexiti.cronoapp.state.CalculatorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculatorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CalculatorState())
    val uiState = _uiState.asStateFlow()

    private var nextId = 1

    fun onNameChange(name: String) {

        _uiState.update { it.copy(newProductName = name, errorMessage = null) }
    }

    fun onPriceChange(price: String) {

        _uiState.update { it.copy(newProductPrice = price, errorMessage = null) }
    }

    fun addProduct() {
        val newName = _uiState.value.newProductName
        val newPrice = _uiState.value.newProductPrice.toDoubleOrNull()


        if (newName.isBlank() || newPrice == null) {
            _uiState.update {
                it.copy(errorMessage = "El precio o el nombre no son válidos.")
            }
            return
        }

        val newProduct = ConciertoItem(
            id = nextId++,
            nombre = newName,
            precio = newPrice
        )

        _uiState.update {
            it.copy(
                productList = it.productList + newProduct,
                newProductName = "",
                newProductPrice = "",
                errorMessage = null // Ensure error is cleared on success
            )
        }
    }

    fun removeProduct(productId: Int) {
        _uiState.update {
            it.copy(
                productList = it.productList.filter { product -> product.id != productId }
            )
        }
    }
}
*/