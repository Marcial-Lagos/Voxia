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
        private val CLAVE_PERFIL_SELECCIONADO =
            intPreferencesKey("perfil_seleccionado_id")
    }

    val perfilSeleccionadoId: Flow<Int?> =
        context.dataStore.data.map { preferencias ->
            preferencias[CLAVE_PERFIL_SELECCIONADO]
        }

    suspend fun seleccionarPerfil(perfilId: Int) {
        context.dataStore.edit { preferencias ->
            preferencias[CLAVE_PERFIL_SELECCIONADO] = perfilId
        }
    }
}
