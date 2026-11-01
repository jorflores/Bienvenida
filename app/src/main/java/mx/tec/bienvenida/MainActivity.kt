package mx.tec.bienvenida

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.tec.bienvenida.navigation.AppNavigation
import mx.tec.bienvenida.ui.theme.BienvenidaTheme
import mx.tec.bienvenida.ui.viewmodel.PrefsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Un solo ViewModel para toda la app: el tema y las pantallas leen el mismo estado.
            val vm: PrefsViewModel = viewModel()
            val estado by vm.estado.collectAsState()

            // El tema oscuro viene de las preferencias guardadas, no del sistema.
            BienvenidaTheme(darkTheme = estado.prefs.temaOscuro) {
                AppNavigation(vm = vm)
            }
        }
    }
}
