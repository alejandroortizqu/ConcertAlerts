package com.mexiti.cronoapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Platillo(
    @StringRes val stringResourceId: Int,
    @DrawableRes val drawableResourceId: Int,
    val ubicacion: String,
    val fecha: String
)
