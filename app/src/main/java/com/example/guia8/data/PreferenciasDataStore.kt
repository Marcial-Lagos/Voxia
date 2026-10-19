package com.example.guia8.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "voxia_preferencias")

class PreferenciasDataStore(
    private val context: Context
) {

    companion object {
        private val CLAVE_MEJOR_PUNTAJE = intPreferencesKey("mejor_puntaje")
    }

    val mejorPuntaje: Flow<Int> = context.dataStore.data.map { preferencias ->
        preferencias[CLAVE_MEJOR_PUNTAJE] ?: 0
    }

    suspend fun guardarMejorPuntaje(puntaje: Int) {
        context.dataStore.edit { preferencias ->
            preferencias[CLAVE_MEJOR_PUNTAJE] = puntaje
        }
    }
}
