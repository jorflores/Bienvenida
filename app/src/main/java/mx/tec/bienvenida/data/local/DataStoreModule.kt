package mx.tec.bienvenida.data.local

import android.R.attr.name
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

// ============================================================
// TODO (Paso 2): crea aquí la instancia de DataStore.
//
// - Va FUERA de cualquier clase (nivel superior del archivo).
// - Es una extensión de Context: val Context.dataStore ...
// - Usa preferencesDataStore(name = "...") con el nombre de archivo que quieras.
// - Declárala UNA sola vez en toda la app.
// ============================================================


val Context.dataStore: DataStore<Preferences>
by preferencesDataStore(name = "configuracion")