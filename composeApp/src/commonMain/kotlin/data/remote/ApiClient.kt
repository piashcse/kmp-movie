package data.remote

import constant.AppConstant
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kmp_movie.composeApp.BuildConfig
import kotlinx.serialization.json.Json

private const val TIMEOUT_MILLIS = 30000L

val apiClient = HttpClient {
    defaultRequest {
        url {
            takeFrom(AppConstant.BASE_URL)
            parameters.append("api_key", BuildConfig.API_KEY)
        }
    }

    expectSuccess = false // Changed to false to handle errors properly with Sandwich

    install(HttpTimeout) {
        connectTimeoutMillis = TIMEOUT_MILLIS
        requestTimeoutMillis = TIMEOUT_MILLIS
        socketTimeoutMillis = TIMEOUT_MILLIS
    }

    install(Logging) {
        level = LogLevel.HEADERS
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
    }

    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        })
    }
}