package mx.tec.bienvenida.data.repository

// Todo lo que la app guarda. Los valores por defecto son los de la primera vez que se abre.
// (Este archivo ya está completo: no hay que cambiarlo.)
data class Preferencias(
    val nombre: String = "",
    val vioBienvenida: Boolean = false,
    val aperturas: Int = 0,
    val temaOscuro: Boolean = false,
)
