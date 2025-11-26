package com.mexiti.cronoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items_concierto")
data class ConciertoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Usaremos esto como clave única

    @ColumnInfo(name = "nombre")
    var nombre: String,

    @ColumnInfo(name = "precio")
    var precio: Double
)




/*package com.mexiti.cronoapp.model

data class ConciertoItem(
    val id: Int,
    val nombre: String,
    val precio: Double
)*/
