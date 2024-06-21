package network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import network.model.RocketLaunchDTO

class SpaceXApi(val httpClient: HttpClient) {

    suspend fun getAllLaunches(): List<RocketLaunchDTO> {
        return httpClient.get("launches").body()
    }
}