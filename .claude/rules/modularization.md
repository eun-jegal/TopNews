## Core Philosophy

- **Feature-layered modularization**: split by feature first, then by layer within each feature.
- **Clean Architecture layers**: `presentation` → `domain` ← `data`. Domain is innermost and depends on nothing.
- **Code lives in a feature module unless it is needed by more than one feature** — then it moves to the appropriate `core` submodule.
- Features **never depend on each other**. Cross-feature shared data belongs in `core:domain` (domain models) or `core:presentation` (shared composables/UI logic), not in the owning feature.

---

## Module Layout

```
:app                            ← @HiltAndroidApp Application, MainActivity, nav graph
:build-logic                    ← Gradle convention plugins
:core:domain                    ← Shared domain models, repository interfaces, error types, Result
:core:data                      ← Shared data logic, Retrofit/OkHttp setup + Hilt NetworkModule, shared DB schemas/DAOs
:core:presentation              ← Shared UI utilities (ObserveAsEvents, UiText, etc.)
:core:design-system             ← Reusable Compose components, colors, theme, typography
:feature:<name>:domain          ← Feature-specific domain models, repo interfaces, error types
:feature:<name>:data            ← Repo implementations, DTOs, mappers, Retrofit API interfaces, Room DAOs, Hilt modules
:feature:<name>:presentation    ← @HiltViewModel ViewModels, screen composables, state, actions, events
```

For standalone, self-contained concerns that involve meaningful complexity (multiple classes, configuration, or a non-trivial API surface), create a dedicated module under `:core` (e.g., `:core:location`, `:core:analytics`). Do not create a separate module for a single class or a trivial utility — that belongs in an existing `core` module instead.

A shared Room database is a good candidate for a dedicated `:core:database` module. It contains the `@Database` class, all entity definitions, all DAOs, migrations, and the Hilt `DatabaseModule` that provides the database and DAOs. Feature modules that need DB access depend on `:core:database` directly, while features that don't need it remain decoupled.

---

## Dependency Rules

| Layer | May depend on |
|---|---|
| `presentation` | `domain` (own feature), `core:domain`, `core:presentation`, `core:design-system` |
| `data` | `domain` (own feature), `core:domain`, `core:data` |
| `domain` | `core:domain` only — never `data` or `presentation` |
| `:app` | everything (wires all modules) |

**Every** layer and module may access `core:domain`.

---

## Dependency Injection with Hilt

- `:app` holds the `@HiltAndroidApp` Application class and the `@AndroidEntryPoint` Activity. Because Hilt aggregates every `@Module` at compile time, `:app` must depend on every module that contributes bindings.
- Each `data` module owns a Hilt module that binds its implementations to the domain interfaces with `@Binds`, installed in `SingletonComponent`:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class NoteDataModule {
    @Binds @Singleton
    abstract fun bindNoteRemoteDataSource(impl: RetrofitNoteDataSource): NoteRemoteDataSource
}
```

- Retrofit API interfaces are provided with `@Provides` in the same feature `data` module (`retrofit.create(NoteApi::class.java)`); the shared `OkHttpClient` and `Retrofit` instances come from `NetworkModule` in `:core:data`.
- Implementations use `@Inject constructor(...)`. `domain` modules stay pure Kotlin — they only declare interfaces and never reference Hilt.
- ViewModels are annotated with `@HiltViewModel` and obtained in composables via `hiltViewModel()`.

---

## Convention Plugins (`:build-logic`)

Define a convention plugin for every non-trivial Gradle config:

| Plugin | Purpose |
|---|---|
| `android-application` | App module config (applicationId, versionCode, etc.) + Hilt |
| `android-library` | Base Android library config |
| `android-feature` | Android library + Compose + Hilt (incl. `hilt-navigation-compose`) + shared feature deps bundled |
| `domain-module` | Pure Kotlin (JVM) module, no Android deps |
| `compose` | Compose compiler + BOM |
| `hilt` | Hilt Gradle plugin + KSP + `hilt-android` / `hilt-compiler` |
| `retrofit` | Retrofit + OkHttp (+ logging interceptor) + KotlinX Serialization converter |
| `room` | Room + KSP config |
| `kotlinx-serialization` | KotlinX Serialization plugin + dep |

Use **version catalogs** (`libs.versions.toml`) for all dependency and version management. No hardcoded versions in build files.

---

## Key Libraries

| Concern | Library |
|---|---|
| DI | Hilt (+ `hilt-navigation-compose`, `hilt-work`) |
| Networking | Retrofit + OkHttp |
| Local DB | Room |
| Preferences | DataStore |
| Navigation | Compose Navigation (type-safe) |
| Serialization | KotlinX Serialization (Retrofit converter + Nav routes) |
| Image loading | Coil |
| Logging | Kermit |
| Async | Coroutines + Flow |
| Background tasks | WorkManager (`@HiltWorker`) |
| Secrets | `local.properties` + `BuildConfig` |
| Testing | JUnit5, Turbine, AssertK, `kotlinx-coroutines-test`, OkHttp `MockWebServer` |
| UI testing | `ComposeTestRule`, `hilt-android-testing` |

---

## Checklist: Adding a New Feature Module

- [ ] Create `:feature:<name>:domain`, `:feature:<name>:data`, `:feature:<name>:presentation` modules
- [ ] Apply appropriate convention plugins to each module (`domain-module`, `android-library`/`android-feature`, `hilt`, `retrofit` where needed)
- [ ] Add a Hilt `@Module` in `:feature:<name>:data` binding implementations to domain interfaces and providing the feature's Retrofit API
- [ ] Add the new modules as dependencies of `:app` so Hilt can see their bindings
- [ ] Verify no cross-feature dependencies are introduced
- [ ] If logic is shared across 2+ features, extract to the appropriate `core` submodule