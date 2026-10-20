package com.example.guia8.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PerfilDao {

    @Insert
    suspend fun insertarPerfil(perfil: PerfilEntity): Long

    @Query("SELECT * FROM perfiles ORDER BY id DESC")
    fun obtenerPerfiles(): Flow<List<PerfilEntity>>

    @Query("SELECT * FROM perfiles WHERE id = :perfilId LIMIT 1")
    fun obtenerPerfilPorId(perfilId: Int): Flow<PerfilEntity?>

    @Query(
        """
        UPDATE perfiles
        SET mejorPuntaje = :puntaje
        WHERE id = :perfilId
          AND :puntaje > mejorPuntaje
        """
    )
    suspend fun actualizarMejorPuntaje(
        perfilId: Int,
        puntaje: Int
    )
}
