package mx.tec.bienvenida.data.local

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

// ============================================================
// TODO (Paso 3): define aquí las claves de lo que se guarda.
//
// Necesitas 4 claves, una por cada campo de Preferencias:
//   nombre          -> String
//   vioBienvenida   -> Boolean
//   aperturas       -> Int
//   temaOscuro      -> Boolean
//
// Tip: agrúpalas en un object PrefKeys { ... } y usa
// stringPreferencesKey, booleanPreferencesKey e intPreferencesKey.
// ============================================================

object PrefKeys{
    val NOMBRE = stringPreferencesKey("nombre")
    val VIO_BIENVENIDA = booleanPreferencesKey("vio_bienvenida")
    val APERTURAS = intPreferencesKey("aperturas")
    val TEMA_OSCURO = booleanPreferencesKey("tema_oscuro")
}