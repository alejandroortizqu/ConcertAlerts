package com.mexiti.cronoapp.model

import androidx.annotation.DrawableRes


data class Concert(
    @DrawableRes val drawableResourceId: Int,
    val stringResourceId: String,
    val ubicacion: String,
    val fecha: String
)
