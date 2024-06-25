package network

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun getHttpClient() = HttpClient {
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.d(message = message, tag = "KTOR")
                }
            }
        }

        install(DefaultRequest) {
            url("https://api.spacexdata.com/")
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        install(ContentNegotiation) {
            json(
                Json {
                    encodeDefaults = false
                    ignoreUnknownKeys = true
                }
            )
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30_000L
            connectTimeoutMillis = 30_000L
            socketTimeoutMillis = 30_000L
        }
    }