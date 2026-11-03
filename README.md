# Ejercicio de DataStore

App en Kotlin + Jetpack Compose que **ya funciona**, pero guarda todo **en memoria**. Tu objetivo es hacer que recuerde los datos usando **Preferences DataStore**.

## Qué hace la app

1. **Bienvenida** (solo la primera vez): pide tu nombre y tiene un botón **Comenzar**.
2. **Inicio**: dice "Hola, *nombre*" y cuántas veces has abierto la app.
3. **Configuración** (engrane ⚙): un switch de **tema oscuro** y un botón **Borrar mis datos**, que regresa a la bienvenida.

## El problema

Corre la app, escribe tu nombre, activa el tema oscuro y luego **cierra la app desde recientes** (deslízala hacia arriba). Al volver a abrirla:

- Te pide el nombre otra vez.
- El contador vuelve a 1.
- El tema regresa a claro.

Todo vive en `PrefsRepository`, dentro de un `MutableStateFlow`, y la memoria se borra al cerrar la app.

## Tu objetivo

Sigue los pasos de la presentación de DataStore. Cada archivo que tienes que tocar tiene un comentario `TODO (Paso N)`.

| Paso | Archivo | Qué hacer |
|---|---|---|
| 1 | `gradle/libs.versions.toml` y `app/build.gradle.kts` | Agrega la dependencia `implementation("androidx.datastore:datastore-preferences:1.3.0")` (Opción A con catálogo u Opción B directa). Después, **Sync Now**. |
| 2 | `data/local/DataStoreModule.kt` | Crea la instancia `Context.dataStore` con `preferencesDataStore`. |
| 3 | `data/local/PrefKeys.kt` | Define 4 claves: nombre (String), vioBienvenida (Boolean), aperturas (Int) y temaOscuro (Boolean). |
| 4 | `data/repository/PrefsRepository.kt` | Cambia la memoria por DataStore: `prefsFlow` con `data.map { }`, cada función con `edit { }` y `borrarTodo()` con `clear()`. **No cambies los nombres ni los tipos de las funciones.** |
| 5 | `ui/viewmodel/PrefsViewModel.kt` | Cambia `PrefsRepository()` por `PrefsRepository(app)`. |

**No necesitas modificar** las pantallas, la navegación, `MainActivity` ni `Preferencias.kt`. Si el repositorio queda bien, todo lo demás funciona solo.

## Cómo saber que ya quedó

| Prueba | Resultado esperado |
|---|---|
| Escribe tu nombre, cierra la app desde recientes y ábrela | Entra directo a **Inicio** con tu nombre |
| Cierra y abre la app 3 veces | El contador sube cada vez |
| Activa el tema oscuro, cierra y abre | Sigue en tema oscuro |
| Configuración > **Borrar mis datos**, cierra y abre | Vuelve a pedir el nombre y el contador empieza en 1 |

## Pistas

- Los valores que nunca se han guardado regresan `null`: usa `?:` con el valor por defecto (`""`, `false` o `0`).
- Para sumar 1 al contador, lee el valor actual **dentro** de `edit { p -> ... }`.
- La instancia de DataStore se declara **una sola vez**, fuera de cualquier clase. Si hay dos instancias del mismo archivo, la app truena.
- Puedes ver el archivo en Android Studio con **Device Explorer**: `/data/data/mx.tec.bienvenida/files/datastore/`.

## Abrir el proyecto

1. **File > Open** y selecciona la carpeta `Bienvenida` (la que contiene `settings.gradle.kts`).
2. Espera el **Gradle Sync**. Si Android Studio sugiere actualizar algo, acepta.
3. Corre la app con ▶.

## Estructura

```
mx.tec.bienvenida
├── MainActivity.kt                 Crea el ViewModel y aplica el tema guardado
├── navigation
│   ├── Rutas.kt                    bienvenida, principal, configuracion
│   └── AppNavigation.kt            Elige la pantalla inicial según vioBienvenida
├── data
│   ├── local
│   │   ├── DataStoreModule.kt      TODO Paso 2
│   │   └── PrefKeys.kt             TODO Paso 3
│   └── repository
│       ├── Preferencias.kt         Los 4 datos que se guardan (ya está completo)
│       └── PrefsRepository.kt      TODO Paso 4: hoy guarda en memoria
└── ui
    ├── viewmodel
    │   └── PrefsViewModel.kt       TODO Paso 5
    ├── screens                     Bienvenida, Principal, Configuracion
    └── theme                       Colores, tema y tipografía
```
