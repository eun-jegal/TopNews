# Core Principles

## Error Handling

Data sources and repositories never throw — they return `Result<D, E>`. These types live in `:core:domain` (pure Kotlin) under `com.ejcode.topnews.core.domain.util`:

```kotlin
interface Error

sealed interface DataError : Error {
    enum class Network : DataError {
        REQUEST_TIMEOUT,
        NO_INTERNET,
        UNAUTHORIZED,
        CONFLICT,
        PAYLOAD_TOO_LARGE,
        TOO_MANY_REQUESTS,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local : DataError {
        DISK_FULL,
        NOT_FOUND,
        UNKNOWN
    }
}

// Alias avoids a name clash with the nested Result.Error class
typealias RootError = Error

sealed interface Result<out D, out E : RootError> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : RootError>(val error: E) : Result<Nothing, E>
}

typealias EmptyResult<E> = Result<Unit, E>

inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> = when (this) {
    is Result.Success -> Result.Success(map(data))
    is Result.Error -> this
}

inline fun <T, E : Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    if (this is Result.Success) action(data)
    return this
}

inline fun <T, E : Error> Result<T, E>.onFailure(action: (E) -> Unit): Result<T, E> {
    if (this is Result.Error) action(error)
    return this
}

fun <T, E : Error> Result<T, E>.asEmptyResult(): EmptyResult<E> = map { }
```

- The helpers are `inline`, so their lambdas can call `suspend` functions (e.g. `_events.send(...)` inside `onFailure`).
- `onSuccess` / `onFailure` are for side effects and always return the **original** result. To fall back to another source, branch with `when` instead (see the repository example below).
- Feature-specific errors implement `Error` in the feature's `domain` module.

---

## Data Source vs Repository

- **Data source** — accesses a single data source (local DB, remote API, file system). Most classes in the data layer are data sources.
- **Repository** — combines multiple data sources (e.g., a remote API + a local DB for offline-first). Only use the term "repository" when the class genuinely coordinates multiple sources.

```kotlin
// Single source → data source
interface NoteLocalDataSource {
    suspend fun getNotes(): Result<List<Note>, DataError.Local>
    suspend fun insertNotes(notes: List<Note>): EmptyResult<DataError.Local>
}

interface NoteRemoteDataSource {
    suspend fun fetchNotes(): Result<List<Note>, DataError.Network>
    suspend fun deleteNote(id: String): EmptyResult<DataError.Network>
}

// Multiple sources → repository
interface NoteRepository {
    suspend fun getNotes(): Result<List<Note>, DataError>
    suspend fun sync(): EmptyResult<DataError>
}
```

## Domain Layer Contracts

- Pure Kotlin — no Android/framework imports.
- Contains: domain models, data source/repository **interfaces**, error types.
- **Every data source or repository used by a ViewModel must have an interface in `domain`** — enforces that `presentation` never depends on `data`, and enables testing.

---

## DTOs and Domain Models

- Always separate: DTOs (data layer) ↔ Domain Models (domain layer).
- Domain models never go directly into Room entities or Retrofit request/response bodies.
- Mappers are simple extension functions living in the data layer alongside the DTO:

```kotlin
fun NoteDto.toNote(): Note = Note(id = id, title = title, ...)
fun Note.toNoteDto(): NoteDto = NoteDto(id = id, title = title, ...)
fun NoteEntity.toNote(): Note = ...
fun Note.toNoteEntity(): NoteEntity = ...
```

---

## Implementations

Name implementations for what makes them unique — never suffix with `Impl`.

### Data source (single source)

```kotlin
class RoomNoteDataSource @Inject constructor(
    private val dao: NoteDao
) : NoteLocalDataSource {
    override suspend fun getNotes(): Result<List<Note>, DataError.Local> {
        return try {
            Result.Success(dao.getAllNotes().map { it.toNote() })
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}
```

### Repository (multiple sources)

```kotlin
class OfflineFirstNoteRepository @Inject constructor(
    private val localDataSource: NoteLocalDataSource,
    private val remoteDataSource: NoteRemoteDataSource
) : NoteRepository {
    // Remote first; on any network error, fall back to the local cache
    override suspend fun getNotes(): Result<List<Note>, DataError> {
        return when (val remote = remoteDataSource.fetchNotes()) {
            is Result.Success -> {
                localDataSource.insertNotes(remote.data)
                remote
            }
            is Result.Error -> localDataSource.getNotes()
        }
    }

    override suspend fun sync(): EmptyResult<DataError> { /* ... */ }
}
```

Use names like `RoomNoteDataSource`, `RetrofitNoteDataSource`, `OfflineFirstNoteRepository`. The name should tell you what the class wraps or how it behaves.

All implementations use `@Inject constructor(...)` and are bound to their domain interfaces with `@Binds` in the feature's `data` Hilt module:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class NoteDataModule {

    @Binds @Singleton
    abstract fun bindNoteLocalDataSource(impl: RoomNoteDataSource): NoteLocalDataSource

    @Binds @Singleton
    abstract fun bindNoteRemoteDataSource(impl: RetrofitNoteDataSource): NoteRemoteDataSource

    @Binds @Singleton
    abstract fun bindNoteRepository(impl: OfflineFirstNoteRepository): NoteRepository
}
```

---

## Retrofit — Dependencies

- `com.squareup.retrofit2:retrofit`
- `com.squareup.retrofit2:converter-kotlinx-serialization` (DTOs stay `@Serializable`)
- `com.squareup.okhttp3:logging-interceptor`
- `com.squareup.okhttp3:mockwebserver3` (tests only — the OkHttp 5 artifact; the legacy `mockwebserver` is deprecated)

Manage versions in `libs.versions.toml`.

---

## Retrofit — OkHttp & Retrofit Factories (`core:data`)

Configure OkHttp and Retrofit once. Accept the base URL and `OkHttpClient` externally so tests can point Retrofit at a `MockWebServer`:

```kotlin
object OkHttpClientFactory {
    fun create(
        interceptors: List<Interceptor>,
        isDebug: Boolean,
        authenticator: Authenticator? = null
    ): OkHttpClient = OkHttpClient.Builder()
        .apply { interceptors.forEach(::addInterceptor) }
        .apply { authenticator?.let(::authenticator) }
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = if (isDebug) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
                // Keep credentials out of Logcat
                redactHeader("X-Api-Key")
                redactHeader("Authorization")
            }
        )
        .build()
}

object RetrofitFactory {
    private val json = Json { ignoreUnknownKeys = true }

    fun create(baseUrl: String, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl) // must end with "/"
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
}
```

### API key authentication (TopNews / NewsAPI)

NewsAPI has no user login or token refresh — every request just carries the API key in the `X-Api-Key` header. The key comes from `local.properties` → `BuildConfig.NEWS_API_KEY` in `:core:data`; never hardcode it or put it in a URL query (query strings end up in logs).

```kotlin
@Singleton
class ApiKeyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request().newBuilder()
            .header("X-Api-Key", BuildConfig.NEWS_API_KEY)
            .build()
        return chain.proceed(request)
    }
}
```

Provide `OkHttpClient` and `Retrofit` once via Hilt in `:core:data`. `ApiKeyInterceptor` uses `@Inject constructor`, so Hilt builds it without an explicit `@Provides`:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient =
        OkHttpClientFactory.create(
            interceptors = listOf(apiKeyInterceptor),
            isDebug = BuildConfig.DEBUG
        )

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        RetrofitFactory.create(BuildConfig.BASE_URL, okHttpClient)
}
```

NewsAPI reports a missing/invalid key as `401` (`DataError.Network.UNAUTHORIZED`) and rate limiting as `429` (`TOO_MANY_REQUESTS`).

Each feature provides its own API interface from a Hilt module in its `data` module, using the shared `Retrofit`:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NoteNetworkModule {

    @Provides
    @Singleton
    fun provideNoteApi(retrofit: Retrofit): NoteApi =
        retrofit.create(NoteApi::class.java)
}
```

Enable `buildFeatures { buildConfig = true }` in `:core:data` and define `BASE_URL` there (it must end with `/`).

---

## Retrofit — API Interfaces (`feature:data`)

One API interface per feature, living next to its DTOs. Endpoints are `suspend` functions returning `Response<T>` so the safe call helpers can inspect status codes. Use relative paths (no leading `/`):

```kotlin
interface NoteApi {
    @GET("notes")
    suspend fun getNotes(): Response<List<NoteDto>>

    @POST("notes")
    suspend fun createNote(@Body note: NoteDto): Response<NoteDto>

    @DELETE("notes/{id}")
    suspend fun deleteNote(@Path("id") id: String): Response<Unit>
}
```

---

## Retrofit — Safe Call Helpers (`core:data`)

Wrap every API call in `safeCall` (endpoints with a body) or `safeEmptyCall` (endpoints with no body, e.g. `204 No Content`) so data source call sites stay clean and uniform. Every value it returns is defined in `DataError.Network` (see **Error Handling** above).

```kotlin
suspend inline fun <T, R> safeRequest(
    execute: () -> Response<T>,
    onSuccess: (Response<T>) -> Result<R, DataError.Network>
): Result<R, DataError.Network> {
    val response = try {
        execute()
    } catch (e: CancellationException) {
        throw e
    } catch (e: SocketTimeoutException) {
        return Result.Error(DataError.Network.REQUEST_TIMEOUT)
    } catch (e: IOException) {
        return Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        return Result.Error(DataError.Network.SERIALIZATION)
    } catch (e: Exception) {
        return Result.Error(DataError.Network.UNKNOWN)
    }

    if (response.isSuccessful) return onSuccess(response)

    return Result.Error(
        when (response.code()) {
            401 -> DataError.Network.UNAUTHORIZED
            408 -> DataError.Network.REQUEST_TIMEOUT
            409 -> DataError.Network.CONFLICT
            413 -> DataError.Network.PAYLOAD_TOO_LARGE
            429 -> DataError.Network.TOO_MANY_REQUESTS
            in 500..599 -> DataError.Network.SERVER_ERROR
            else -> DataError.Network.UNKNOWN
        }
    )
}

suspend inline fun <T> safeCall(
    execute: () -> Response<T>
): Result<T, DataError.Network> = safeRequest(execute) { response ->
    response.body()?.let { Result.Success(it) }
        ?: Result.Error(DataError.Network.SERIALIZATION)
}

suspend inline fun safeEmptyCall(
    execute: () -> Response<Unit>
): EmptyResult<DataError.Network> = safeRequest(execute) { Result.Success(Unit) }
```

Call sites in a data source:

```kotlin
class RetrofitNoteDataSource @Inject constructor(
    private val api: NoteApi
) : NoteRemoteDataSource {
    override suspend fun fetchNotes(): Result<List<Note>, DataError.Network> {
        return safeCall { api.getNotes() }.map { dtos -> dtos.map { it.toNote() } }
    }

    override suspend fun deleteNote(id: String): EmptyResult<DataError.Network> {
        return safeEmptyCall { api.deleteNote(id) }
    }
}
```

---

## Token Storage & Refresh (only for APIs with user auth)

**TopNews does not use this** — NewsAPI authenticates with a static API key (see above). Apply this section only if the app later talks to a backend with user login and access/refresh tokens; do not build `TokenStorage`, `AuthApi`, or a refresh flow for NewsAPI.

Store tokens in DataStore (in `core:data` or a dedicated `:core:auth` / `:feature:auth:data` module). An OkHttp `Interceptor` attaches the access token to every request, and an OkHttp `Authenticator` handles 401 refresh automatically.

```kotlin
@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenStorage: TokenStorage
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val token = runBlocking { tokenStorage.getAccessToken() }
        val request = chain.request().newBuilder()
            .apply { token?.let { header("Authorization", "Bearer $it") } }
            .build()
        return chain.proceed(request)
    }
}

@Singleton
class TokenAuthenticator @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val authApi: AuthApi // provided from @AuthRetrofit — a client WITHOUT this authenticator
) : Authenticator {
    @Synchronized
    override fun authenticate(route: Route?, response: okhttp3.Response): Request? {
        if (response.priorResponse != null) return null // already retried once → give up

        // Another request may have refreshed while this one waited on the lock
        val failedToken = response.request.header("Authorization")?.removePrefix("Bearer ")
        val currentToken = runBlocking { tokenStorage.getAccessToken() }
        if (currentToken != null && currentToken != failedToken) {
            return response.request.withToken(currentToken)
        }

        val refreshToken = runBlocking { tokenStorage.getRefreshToken() } ?: return null
        val tokens = runBlocking { authApi.refresh(RefreshRequestDto(refreshToken)) }
            .takeIf { it.isSuccessful }?.body() ?: return null

        runBlocking { tokenStorage.saveTokens(tokens.accessToken, tokens.refreshToken) }
        return response.request.withToken(tokens.accessToken)
    }

    private fun Request.withToken(token: String): Request =
        newBuilder().header("Authorization", "Bearer $token").build()
}
```

The `NetworkModule` then wires the authenticator into the main client and gives `AuthApi` a separate client:

```kotlin
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient = OkHttpClientFactory.create(
        interceptors = listOf(authInterceptor),
        isDebug = BuildConfig.DEBUG,
        authenticator = tokenAuthenticator
    )

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        RetrofitFactory.create(BuildConfig.BASE_URL, okHttpClient)

    // Token refresh uses its own client WITHOUT TokenAuthenticator
    // (prevents a refresh loop and a TokenAuthenticator ↔ OkHttpClient dependency cycle)
    @Provides
    @Singleton
    @AuthRetrofit
    fun provideAuthRetrofit(): Retrofit =
        RetrofitFactory.create(BuildConfig.BASE_URL, OkHttpClient.Builder().build())

    @Provides
    @Singleton
    fun provideAuthApi(@AuthRetrofit retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)
}
```

- `AuthApi` must use its own `OkHttpClient` without `TokenAuthenticator` (the `@AuthRetrofit` instance in `NetworkModule`), or a failing refresh call will loop.
- `TokenStorage` is an interface; bind its DataStore implementation with `@Binds` in the module that owns it.
- When refresh fails (`authenticate` returns `null`), the original call surfaces as `DataError.Network.UNAUTHORIZED` — clear tokens and route the user to login.

---

## Room Migrations

Prefer `@Database(autoMigrations = [AutoMigration(from = 1, to = 2)])`. Use manual `Migration` objects when the schema change is too complex for auto-migration.

---

## Offline-First (when applicable)

Follow **Room as single source of truth**: fetch from network → persist to Room → expose DB `Flow` to the ViewModel. The ViewModel never observes network responses directly.

This pattern is optional — apply it when the project requires offline support.

---

## Naming Conventions

| Thing | Convention | Example |
|---|---|---|
| Data source interface | `<Entity><Local/Remote>DataSource` | `NoteLocalDataSource`, `NoteRemoteDataSource` |
| Data source impl | describe what makes it unique | `RoomNoteDataSource`, `RetrofitNoteDataSource` |
| Retrofit API interface | `<Entity>Api` | `NoteApi` |
| Repository interface | `<Entity>Repository` (multi-source only) | `NoteRepository` |
| Repository impl | describe what makes it unique | `OfflineFirstNoteRepository` |
| DTO | `<Model>Dto` | `NoteDto` |
| Room entity | `<Model>Entity` | `NoteEntity` |
| Mapper | extension fun on source type | `fun NoteDto.toNote()` |

---

## Checklist: Adding a New Data Source or Repository

- [ ] Define domain model(s) in `feature:domain`
- [ ] Define data source or repository interface in `feature:domain`
- [ ] Define feature-specific error type(s) in `feature:domain` (implement `Error`) — see **Error Handling** above
- [ ] Define DTOs and Room entities in `feature:data`
- [ ] Define the Retrofit API interface (`<Entity>Api`) in `feature:data` and provide it with `@Provides` in the feature's Hilt module
- [ ] Bind implementations to domain interfaces with `@Binds` in the feature's Hilt module (`@InstallIn(SingletonComponent::class)`)
- [ ] Write mappers as extension functions in `feature:data`
- [ ] Implement data source (single source) or repository (multi-source) in `feature:data`, named for what makes it unique, wrapping API calls in `safeCall` / `safeEmptyCall`