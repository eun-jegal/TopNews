# TopNews

Clean Architecture + MVI news app. Kotlin 2.x, Jetpack Compose, Android-only.

## Stack (do not substitute)
- DI: Hilt — never Koin
- Networking: Retrofit + OkHttp + kotlinx-serialization — never Ktor
- DB: Room, Prefs: DataStore, Images: Coil

## News API
- Provider: <https://newsapi.org/>, base URL: <https://newsapi.org/v2/>
- API key: `NEWS_API_KEY` in `local.properties` → `BuildConfig`. Never hardcode or commit it.

## Modules (planned)
- Project is being scaffolded; create modules as needed following modularization.md

## Guidelines
Rules in `.claude/rules/` load automatically when you touch matching files.
Before creating new files in a layer, read the matching rule first:
- Screens/ViewModels → `presentation-mvi.md`, `compose-ui.md`
- Data sources/repositories/Room/Retrofit → `data-layer.md`
- New modules or Gradle changes → `modularization.md`
- Navigation → `navigation.md`
- Tests → `testing.md`

## Build & Test
- Assemble: `./gradlew assembleDebug`
- Unit tests: `./gradlew testDebugUnitTest`
- Lint: `./gradlew lintDebug`

## Definition of done
Run unit tests and lint for affected modules before reporting a task complete.