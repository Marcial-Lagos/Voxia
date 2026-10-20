package com.example.guia8.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "perfiles")
data class PerfilEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val correo: String,
    val direccion: String
)
