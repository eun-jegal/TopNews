package com.ejcode.topnews.core.data.network

import com.ejcode.topnews.core.domain.util.DataError
import com.ejcode.topnews.core.domain.util.EmptyResult
import com.ejcode.topnews.core.domain.util.Result
import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import kotlin.coroutines.cancellation.CancellationException

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
