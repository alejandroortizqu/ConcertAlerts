package com.mexiti.cronoapp.state

import com.mexiti.cronoapp.model.ConciertoItem

data class CalculatorState(
    val productList: List<ConciertoItem> = emptyList(),
    val newProductName: String = "",
    val newProductPrice: String = "",
    val errorMessage: String? = null
)
