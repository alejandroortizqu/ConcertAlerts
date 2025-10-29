// app/src/main/java/com/mexiti/cronoapp/data/DataSource.kt
package com.mexiti.cronoapp.data

import com.mexiti.cronoapp.R
import com.mexiti.cronoapp.model.Platillo

class DataSource {
    fun loadPlatillos(): List<Platillo> = listOf(
        Platillo(R.string.desayuno,  R.drawable.tyler, "Palacio de los Deportes", "28 Oct 2025"),
        Platillo(R.string.hamburger, R.drawable.toto,"Foro Sol", "30 Oct 2025"),
        Platillo(R.string.pizza,     R.drawable.weeknd, "Foro Sol", "1 Nov 2025"),
        Platillo(R.string.postre,    R.drawable.guns, "Palacio de los Deportes", "5 Nov 2025"),
        Platillo(R.string.pozole,    R.drawable.edc, "Autódromo Hermanos Rodriguez", "10 Nov 2025"),
        Platillo(R.string.tacos,     R.drawable.nsqk, "Pepsi Center WTC", "12 Nov 2025"),
    )
}
