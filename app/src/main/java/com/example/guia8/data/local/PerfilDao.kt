package com.example.guia8.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PerfilDao {

    @Insert
    suspend fun insertarPerfil(perfil: PerfilEntity)

    @Query("SELECT * FROM perfiles ORDER BY id DESC")
    fun obtenerPerfiles(): Flow<List<PerfilEntity>>
}
