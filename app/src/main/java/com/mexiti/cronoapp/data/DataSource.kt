// app/src/main/java/com/mexiti/cronoapp/data/DataSource.kt
package com.mexiti.cronoapp.data

import com.mexiti.cronoapp.R
import com.mexiti.cronoapp.model.Platillo

class DataSource {
    fun loadPlatillos(): List<Platillo> = listOf(
        Platillo(R.string.desayuno,  R.drawable.icon_timer_24, "Palacio de los Deportes", "28 Oct 2025"),
        Platillo(R.string.hamburger, R.drawable.icon_timer_24,"Foro Sol", "30 Oct 2025"),
        Platillo(R.string.pizza,     R.drawable.icon_timer_24, "Foro Sol", "1 Nov 2025"),
        Platillo(R.string.postre,    R.drawable.icon_timer_24, "Palacio de los Deportes", "5 Nov 2025"),
        Platillo(R.string.pozole,    R.drawable.icon_timer_24, "Autódromo Hermanos Rodriguez", "10 Nov 2025"),
        Platillo(R.string.tacos,     R.drawable.icon_timer_24, "Pepsi Center WTC", "12 Nov 2025"),
    )
}
