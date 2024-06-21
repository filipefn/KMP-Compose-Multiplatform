package network.model

import domain.model.RocketLaunch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RocketLaunchDTO(
    @SerialName("flight_number") val flightNumber: Int?,
    @SerialName("name") val missionName: String?,
    @SerialName("details") val details: String?,
    @SerialName("success") val launchSuccess: Boolean?,
)

fun RocketLaunchDTO.toDomain() =   RocketLaunch(
    flightNumber = flightNumber ?: 0,
    missionName = missionName.orEmpty(),
    details = details.orEmpty(),
    launchSuccess = launchSuccess ?: false
)

