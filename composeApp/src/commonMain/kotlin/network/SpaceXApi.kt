package network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path
import network.model.RocketDTO
import network.model.RocketLaunchDTO

class SpaceXApi(val httpClient: HttpClient) {

    suspend fun getAllRockets(): List<RocketDTO> {
        return httpClient.get {
            url { path("v4/rockets") }
        }.body()
    }

    suspend fun getAllLaunches(): List<RocketLaunchDTO> {
        return httpClient.get {
            url { path("v5/launches") }
        }.body()
    }
}