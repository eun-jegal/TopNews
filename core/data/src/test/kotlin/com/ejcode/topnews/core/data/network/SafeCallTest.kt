package com.ejcode.topnews.core.data.network

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.ejcode.topnews.core.domain.util.DataError
import com.ejcode.topnews.core.domain.util.Result
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import kotlin.coroutines.cancellation.CancellationException

class SafeCallTest {

    @Test
    fun `successful response returns body`() = runTest {
        val result = safeCall { Response.success("headlines") }

        assertThat(result).isEqualTo(Result.Success("headlines"))
    }

    @Test
    fun `successful response without body returns SERIALIZATION`() = runTest {
        val result = safeCall { Response.success<String>(null) }

        assertThat(result).isEqualTo(Result.Error(DataError.Network.SERIALIZATION))
    }

    @Test
    fun `safeEmptyCall ignores missing body`() = runTest {
        val result = safeEmptyCall { Response.success<Unit>(204, null) }

        assertThat(result).isEqualTo(Result.Success(Unit))
    }

    @ParameterizedTest(name = "HTTP {0} -> {1}")
    @CsvSource(
        "401, UNAUTHORIZED",
        "408, REQUEST_TIMEOUT",
        "409, CONFLICT",
        "413, PAYLOAD_TOO_LARGE",
        "429, TOO_MANY_REQUESTS",
        "500, SERVER_ERROR",
        "503, SERVER_ERROR",
        "599, SERVER_ERROR",
        "400, UNKNOWN",
        "404, UNKNOWN"
    )
    fun `error status code maps to network error`(
        code: Int,
        expected: DataError.Network
    ) = runTest {
        val result = safeCall { Response.error<String>(code, "".toResponseBody()) }

        assertThat(result).isEqualTo(Result.Error(expected))
    }

    @Test
    fun `socket timeout maps to REQUEST_TIMEOUT`() = runTest {
        val result = safeCall<String> { throw SocketTimeoutException() }

        assertThat(result).isEqualTo(Result.Error(DataError.Network.REQUEST_TIMEOUT))
    }

    @Test
    fun `IOException maps to NO_INTERNET`() = runTest {
        val result = safeCall<String> { throw IOException() }

        assertThat(result).isEqualTo(Result.Error(DataError.Network.NO_INTERNET))
    }

    @Test
    fun `SerializationException maps to SERIALIZATION`() = runTest {
        val result = safeCall<String> { throw SerializationException("bad json") }

        assertThat(result).isEqualTo(Result.Error(DataError.Network.SERIALIZATION))
    }

    @Test
    fun `unexpected exception maps to UNKNOWN`() = runTest {
        val result = safeCall<String> { throw IllegalStateException() }

        assertThat(result).isEqualTo(Result.Error(DataError.Network.UNKNOWN))
    }

    @Test
    fun `CancellationException is rethrown`() = runTest {
        assertFailure {
            safeCall<String> { throw CancellationException("cancelled") }
        }.isInstanceOf<CancellationException>()
    }
}
