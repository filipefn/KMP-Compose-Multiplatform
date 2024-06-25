package domain.model

import org.jetbrains.compose.resources.StringResource

data class Rocket(
    val id: String,
    val name: String,
    val costPerLaunch: Long,
    val country: String,
    val params: List<RocketParam>,
    val firstStage: RocketStage,
    val secondStage: RocketStage,
    val flickrImage: String
)

data class RocketParam(
    val title: String,
    val value: String
)

data class RocketHeight(
    val meters: Double,
    val feet: Double
)

data class RocketDiameter(
    val meters: Double,
    val feet: Double
)

data class RocketMass(
    val kg: Int,
    val lb: Int
)

data class PayloadWeight(
    val id: String,
    val name: String,
    val kg: Int,
    val lb: Int
)

data class RocketStage(
    val engines: Int,
    val fuelAmountTons: Double,
    val burnTimeSec: Int?
)