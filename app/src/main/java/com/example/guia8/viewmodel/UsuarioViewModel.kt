package com.example.guia8.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.guia8.data.PreferenciasDataStore
import com.example.guia8.data.local.PerfilEntity
import com.example.guia8.data.local.VoxiaDatabase
import com.example.guia8.model.UsuarioErrores
import com.example.guia8.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val _estado = MutableStateFlow(UsuarioUiState())
    val estado: StateFlow<UsuarioUiState> = _estado

    private val perfilDao =
        VoxiaDatabase.obtenerInstancia(application).perfilDao()

    private val preferenciasDataStore =
        PreferenciasDataStore(application)

    val perfiles = perfilDao.obtenerPerfiles()

    val perfilSeleccionadoId =
        preferenciasDataStore.perfilSeleccionadoId

    fun onNombreChange(valor: String) {
        _estado.update {
            it.copy(
                nombre = valor,
                errores = it.errores.copy(nombre = null)
            )
        }
    }

    fun onCorreoChange(valor: String) {
        _estado.update {
            it.copy(
                correo = valor,
                errores = it.errores.copy(correo = null)
            )
        }
    }

    fun onClaveChange(valor: String) {
        _estado.update {
            it.copy(
                clave = valor,
                errores = it.errores.copy(clave = null)
            )
        }
    }

    fun onDireccionChange(valor: String) {
        _estado.update {
            it.copy(
                direccion = valor,
                errores = it.errores.copy(direccion = null)
            )
        }
    }

    fun onAceptarTerminosChange(valor: Boolean) {
        _estado.update {
            it.copy(aceptaTerminos = valor)
        }
    }

    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value

        val errores = UsuarioErrores(
            nombre = if (estadoActual.nombre.isBlank()) {
                "Campo obligatorio"
            } else {
                null
            },
            correo = if (!estadoActual.correo.contains("@")) {
                "Correo inválido"
            } else {
                null
            },
            clave = if (estadoActual.clave.length < 6) {
                "Debe tener al menos 6 caracteres"
            } else {
                null
            },
            direccion = if (estadoActual.direccion.isBlank()) {
                "Campo obligatorio"
            } else {
                null
            }
        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
            errores.direccion
        ).isNotEmpty()

        _estado.update {
            it.copy(errores = errores)
        }

        return !hayErrores && estadoActual.aceptaTerminos
    }

    fun guardarPerfil(onGuardado: () -> Unit) {
        val estadoActual = _estado.value

        val perfil = PerfilEntity(
            nombre = estadoActual.nombre,
            correo = estadoActual.correo,
            direccion = estadoActual.direccion
        )

        viewModelScope.launch {
            val nuevoId = perfilDao.insertarPerfil(perfil).toInt()
            preferenciasDataStore.seleccionarPerfil(nuevoId)
            onGuardado()
        }
    }

    fun seleccionarPerfil(perfilId: Int) {
        viewModelScope.launch {
            preferenciasDataStore.seleccionarPerfil(perfilId)
        }
    }
}
