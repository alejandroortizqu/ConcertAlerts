package com.mexiti.cronoapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey val id: Int = 0,
    val nombre: String = "",
    val ciudad: String = "",
    val sexo: String = "",
    val gustos: String = ""
)

