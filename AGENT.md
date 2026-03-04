# AI Agent Instructions

## Project Overview
**Fused User Preferences** is an experimental Android project designed to compare and contrast the legacy `SharedPreferences` API with the modern Jetpack `Preferences DataStore`. The project employs **Clean Architecture**, **MVVM**, and **Dependency Inversion** (using Dagger Hilt) to demonstrate how these two data storage mechanisms can be swapped seamlessly at the data layer while providing a unified interface to the domain and UI layers.

### Key Technologies
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose (Material 3)
- **Dependency Injection:** Dagger Hilt
- **Asynchronous Programming:** Kotlin Coroutines & Flow
- **Data Storage:** SharedPreferences & Jetpack Preferences DataStore
- **Logging:** Timber
- **Testing:** JUnit 4, MockK, Robolectric, Espresso, Hilt Testing
- **Static Analysis:** Detekt, Kotlinter

## Architecture
The project is structured following Clean Architecture principles:

- **`domain`**: Contains the core business logic and repository interfaces.
  - `UserPreferencesRepository`: The primary interface for accessing and modifying user preferences.
  - `PreferenceKeys` & `DefaultValues`: Constants for preference keys and their default values.
- **`data`**: Implements the repository interfaces and handles data sources.
  - `SharedPreferencesRepository`: Implementation using the legacy `SharedPreferences`.
  - `PreferencesDataStoreRepository`: Implementation using the modern `DataStore`.
  - `datasources`: Wrappers for `SharedPreferences` and `DataStore` to abstract boilerplate.
- **`ui`**: The presentation layer.
  - `viewmodels`: ViewModels that interact with the repository. Custom Hilt qualifiers (`@SharedPreferences` and `@PreferencesDataStore`) are used to inject the desired implementation.
  - `screens`: Composable functions for the UI.
  - `navigation`: Jetpack Navigation for Compose.
- **`di`**: Hilt modules (`AppModule`, `DataSourcesModule`, `RepositoryModule`, `DispatcherModule`) for dependency management.

## Building and Running
The project requires **Java 21** and **Android Studio Otter** (or newer).

### Key Gradle Commands
- **Build APK:** `./gradlew assembleDebug`
- **Run Unit Tests:** `./gradlew test`
- **Run Instrumented Tests:** `./gradlew connectedAndroidTest`
- **Static Analysis (Detekt):** `./gradlew detekt`
- **Kotlin Linting (Kotlinter):** `./gradlew lintKotlin`
- **Auto-format Kotlin Code:** `./gradlew formatKotlin`

## Development Conventions
- **Dependency Inversion:** Always depend on the `UserPreferencesRepository` interface in the domain/UI layers. Use Hilt qualifiers to select the specific implementation.
- **Reactive UI:** Use Kotlin `StateFlow` to expose preference changes from the repository to the ViewModel and `collectAsStateWithLifecycle` in Compose.
- **Error Handling:** Preference errors are propagated via a `SharedFlow<Throwable>` in the repository and handled in the ViewModel to display error messages (e.g., via Snackbars).
- **Testing:** 
  - Aim for high coverage in the `data` layer using Robolectric for `SharedPreferences` and `DataStore` testing.
  - Use MockK for mocking dependencies in ViewModels and repositories.
  - Use Hilt for instrumented tests in the `androidTest` directory.
- **Code Style:** Adhere to the rules defined in `config/detekt/detekt.yml` and the default Kotlinter settings. Run `./gradlew formatKotlin` before committing.
