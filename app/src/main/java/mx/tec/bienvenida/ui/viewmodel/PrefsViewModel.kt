package mx.tec.bienvenida.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.tec.bienvenida.data.repository.Preferencias
import mx.tec.bienvenida.data.repository.PrefsRepository

// Lo que la UI necesita saber.
data class EstadoUi(
    val cargando: Boolean = true,              // true hasta que llega el primer valor guardado
    val prefs: Preferencias = Preferencias(),
)

// Se crea UNA vez en MainActivity y se comparte con todas las pantallas.
// Es AndroidViewModel porque, con DataStore, el repositorio necesitará el Context (app).
class PrefsViewModel(app: Application) : AndroidViewModel(app) {

    // TODO (Paso 5): cuando PrefsRepository reciba un Context, cámbialo a PrefsRepository(app).
    private val repo = PrefsRepository()

    private val _estado = MutableStateFlow(EstadoUi())
    val estado: StateFlow<EstadoUi> = _estado.asStateFlow()

    init {
        viewModelScope.launch {
            // Cada vez que se abre la app, el contador sube 1.
            repo.sumarApertura()
            // Escucha los cambios: cada vez que se guarda algo, la UI se actualiza sola.
            repo.prefsFlow.collect { prefs ->
                _estado.value = EstadoUi(cargando = false, prefs = prefs)
            }
        }
    }

    fun guardarNombre(nombre: String) {
        viewModelScope.launch { repo.guardarNombre(nombre.trim()) }
    }

    fun cambiarTema(oscuro: Boolean) {
        viewModelScope.launch { repo.cambiarTema(oscuro) }
    }

    fun borrarDatos() {
        viewModelScope.launch { repo.borrarTodo() }
    }
}
