package com.mexiti.cronoapp.data

import com.mexiti.cronoapp.R
import com.mexiti.cronoapp.model.Concert

class DataSource {
    fun loadConcerts(): List<Concert> = listOf(
        Concert(R.drawable.tyler, "Tyler, The Creator", "Palacio de los Deportes", "28 Oct 2025"),
        Concert(R.drawable.toto, "Toto","Foro Sol", "30 Oct 2025"),
        Concert(R.drawable.weeknd, "The Weeknd", "Foro Sol", "1 Nov 2025"),
        Concert(R.drawable.guns, "Guns N' Roses", "Palacio de los Deportes", "5 Nov 2025"),
        Concert(R.drawable.edc, "EDC México", "Autódromo Hermanos Rodriguez", "10 Nov 2025"),
        Concert(R.drawable.nsqk, "NSQK", "Pepsi Center WTC", "12 Nov 2025"),
    )
}
