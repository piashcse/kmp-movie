package data.remote

import constant.AppConstant
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kmp_movie.composeApp.BuildConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

private const val TIMEOUT_MILLIS = 30000L

@OptIn(ExperimentalSerializationApi::class)
val apiClient = HttpClient {
    defaultRequest {
        url {
            takeFrom(AppConstant.BASE_URL)
            parameters.append("api_key", BuildConfig.API_KEY)
        }
    }

    expectSuccess = true

    install(HttpTimeout) {
        connectTimeoutMillis = TIMEOUT_MILLIS
        requestTimeoutMillis = TIMEOUT_MILLIS
        socketTimeoutMillis = TIMEOUT_MILLIS
    }

    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            explicitNulls = false
            namingStrategy = JsonNamingStrategy.SnakeCase
        })
    }
}
