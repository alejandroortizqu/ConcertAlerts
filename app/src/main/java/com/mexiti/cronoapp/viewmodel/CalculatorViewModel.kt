package com.mexiti.cronoapp.viewmodel

import androidx.lifecycle.ViewModel
import com.mexiti.cronoapp.model.Gasto
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

        val newProduct = Gasto(
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
