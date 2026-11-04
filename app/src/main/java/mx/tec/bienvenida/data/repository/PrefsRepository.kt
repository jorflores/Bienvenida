package mx.tec.bienvenida.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import mx.tec.bienvenida.data.local.PrefKeys
import mx.tec.bienvenida.data.local.dataStore

// ============================================================
// TODO (Paso 4): este repositorio guarda todo EN MEMORIA.
// Por eso, al cerrar la app desde recientes, se pierde todo:
// el nombre, el contador y el tema.
//
// Tu tarea: reemplazar la memoria por DataStore SIN cambiar
// los nombres ni los tipos de las funciones. Así el ViewModel
// y las pantallas siguen funcionando igual.
//
//   1. Agrega al constructor: (private val context: Context)
//   2. prefsFlow  -> context.dataStore.data.map { p -> Preferencias(...) }
//   3. Cada función suspend -> context.dataStore.edit { p -> ... }
//   4. borrarTodo() -> dentro de edit usa p.clear()
//   5. Borra _memoria y sus imports.
// ============================================================
class PrefsRepository (private val context: Context) {

    val prefsFlow: Flow<Preferencias> =
        context.dataStore.data.map { conf ->
            Preferencias(
                nombre = conf[PrefKeys.NOMBRE] ?: "",
                vioBienvenida = conf[PrefKeys.VIO_BIENVENIDA] ?: false,
                aperturas = conf[PrefKeys.APERTURAS] ?: 0,
                temaOscuro = conf[PrefKeys.TEMA_OSCURO] ?: false
            )
        }

    suspend fun sumarApertura() {
        context.dataStore.edit { conf ->
            val actual = conf[PrefKeys.APERTURAS] ?: 0
            conf[PrefKeys.APERTURAS] = actual + 1
        }
    }

    suspend fun guardarNombre(nombre: String) {
        context.dataStore.edit { conf ->
            conf[PrefKeys.NOMBRE] = nombre
            conf[PrefKeys.VIO_BIENVENIDA] = true
        }
    }

    suspend fun cambiarTema(oscuro: Boolean) {
        context.dataStore.edit { conf ->
            conf[PrefKeys.TEMA_OSCURO] = oscuro
        }
    }

    suspend fun borrarTodo() {
        context.dataStore.edit { conf ->
            conf.clear()
        }
    }

}
