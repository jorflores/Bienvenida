package mx.tec.bienvenida.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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
class PrefsRepository {

    // BORRAR al usar DataStore: esta "memoria" desaparece al cerrar la app.
    private val _memoria = MutableStateFlow(Preferencias())

    // Emite un valor nuevo cada vez que algo cambia.
    val prefsFlow: Flow<Preferencias> = _memoria.asStateFlow()

    suspend fun guardarNombre(nombre: String) {
        // Guardar el nombre también marca que ya vio la bienvenida.
        _memoria.update { it.copy(nombre = nombre, vioBienvenida = true) }
    }

    suspend fun sumarApertura() {
        // Con DataStore: lee el valor actual dentro de edit { } (si no existe, es 0) y súmale 1.
        _memoria.update { it.copy(aperturas = it.aperturas + 1) }
    }

    suspend fun cambiarTema(oscuro: Boolean) {
        _memoria.update { it.copy(temaOscuro = oscuro) }
    }

    suspend fun borrarTodo() {
        _memoria.value = Preferencias()
    }
}
