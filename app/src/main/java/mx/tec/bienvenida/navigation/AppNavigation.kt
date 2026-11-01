package mx.tec.bienvenida.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.tec.bienvenida.ui.screens.BienvenidaScreen
import mx.tec.bienvenida.ui.screens.ConfiguracionScreen
import mx.tec.bienvenida.ui.screens.PrincipalScreen
import mx.tec.bienvenida.ui.viewmodel.PrefsViewModel

@Composable
fun AppNavigation(vm: PrefsViewModel) {
    val navController = rememberNavController()
    val estado by vm.estado.collectAsState()

    // Mientras llegan los datos guardados (una fracción de segundo), mostramos una carga.
    if (estado.cargando) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        // Si ya vio la bienvenida, abre directo en la principal.
        // remember: la pantalla inicial se decide UNA sola vez al abrir la app.
        val pantallaInicial = remember {
            if (estado.prefs.vioBienvenida) Rutas.PRINCIPAL else Rutas.BIENVENIDA
        }

        NavHost(navController = navController, startDestination = pantallaInicial) {

            composable(Rutas.BIENVENIDA) {
                BienvenidaScreen(
                    onComenzar = { nombre ->
                        vm.guardarNombre(nombre)
                        navController.navigate(Rutas.PRINCIPAL) {
                            // Con "atrás" no se regresa a la bienvenida.
                            popUpTo(Rutas.BIENVENIDA) { inclusive = true }
                        }
                    }
                )
            }

            composable(Rutas.PRINCIPAL) {
                val e by vm.estado.collectAsState()
                PrincipalScreen(
                    nombre = e.prefs.nombre,
                    aperturas = e.prefs.aperturas,
                    onAbrirConfiguracion = { navController.navigate(Rutas.CONFIGURACION) },
                )
            }

            composable(Rutas.CONFIGURACION) {
                val e by vm.estado.collectAsState()

                // Si se borraron los datos, vioBienvenida vuelve a false: regresamos a la bienvenida.
                LaunchedEffect(e.prefs.vioBienvenida) {
                    if (!e.prefs.vioBienvenida) {
                        navController.navigate(Rutas.BIENVENIDA) {
                            // Vacía la pila completa: con "atrás" no se regresa a nada.
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }

                ConfiguracionScreen(
                    temaOscuro = e.prefs.temaOscuro,
                    onCambiarTema = { vm.cambiarTema(it) },
                    onBorrarDatos = { vm.borrarDatos() },
                    onRegresar = { navController.popBackStack() },
                )
            }
        }
    }
}
