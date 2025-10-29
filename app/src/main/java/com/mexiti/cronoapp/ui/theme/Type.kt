package com.mexiti.cronoapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mexiti.cronoapp.R


val LatoRegular = FontFamily(
    Font(R.font.lato_regular)
)
val LatoBold = FontFamily(
    Font(R.font.lato_bold)
)
val LatoBlack = FontFamily(
    Font(R.font.lato_black)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = LatoBlack,
        fontWeight = FontWeight.Normal,
        fontSize = 25.sp
    ),

    displayMedium = TextStyle(
        fontFamily = LatoRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp
    ),
    labelSmall = TextStyle(
        fontFamily = LatoBold,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = LatoBlack,
        fontWeight = FontWeight.Normal,
        fontSize = 25.sp,
    ),



)