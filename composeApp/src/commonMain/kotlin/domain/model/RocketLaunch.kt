package domain.model

data class RocketLaunch(
    val flightNumber: Int,
    val missionName: String,
    val details: String,
    val launchSuccess: Boolean,
)