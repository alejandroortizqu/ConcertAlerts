package com.mexiti.cronoapp.state

import com.mexiti.cronoapp.model.Gasto

data class CalculatorState(
    val productList: List<Gasto> = emptyList(),
    val newProductName: String = "",
    val newProductPrice: String = "",
    val errorMessage: String? = null
)
