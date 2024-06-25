package network.model

import domain.model.PayloadWeight
import domain.model.Rocket
import domain.model.RocketDiameter
import domain.model.RocketHeight
import domain.model.RocketMass
import domain.model.RocketParam
import domain.model.RocketStage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import spacex.composeapp.generated.resources.Res

@Serializable
data class RocketDTO(
    val id: String,
    val name: String,
    @SerialName("cost_per_launch")
    val costPerLaunch: Long,
    val country: String,
    val height: ApiHeight,
    val diameter: ApiDiameter,
    val mass: ApiMass,
    @SerialName("payload_weights")
    val payloadWeights: List<ApiPayloadWeight>,
    @SerialName("first_stage")
    val firstStage: ApiFirstStage,
    @SerialName("second_stage")
    val secondStage: ApiSecondStage,
    @SerialName("flickr_images")
    val flickrImages: List<String>
)

@Serializable
data class ApiHeight(
    val meters: Double,
    val feet: Double
)

@Serializable
data class ApiDiameter(
    val meters: Double,
    val feet: Double
)

@Serializable
data class ApiMass(
    val kg: Int,
    val lb: Int
)

@Serializable
data class ApiPayloadWeight(
    val id: String,
    val name: String,
    val kg: Int,
    val lb: Int
)

@Serializable
data class ApiFirstStage(
    val engines: Int,
    @SerialName("fuel_amount_tons")
    val fuelAmountTons: Double,
    @SerialName("burn_time_sec")
    val burnTimeSec: Int? = null
)

@Serializable
data class ApiSecondStage(
    val engines: Int,
    @SerialName("fuel_amount_tons")
    val fuelAmountTons: Double,
    @SerialName("burn_time_sec")
    val burnTimeSec: Int? = null
)


internal fun RocketDTO.toDomain(): Rocket {
    return Rocket(
        id = id,
        name = name,
        costPerLaunch = costPerLaunch,
        country = country,
        params = listOf(
            RocketParam(title = height.meters.toString(), value = height.feet.toString()),
            RocketParam(title = diameter.meters.toString(), value = diameter.feet.toString()),
            RocketParam(title = mass.kg.toString(), value = mass.lb.toString()),
            payloadWeights.first().let {
                RocketParam(title = it.name, value = it.lb.toString())
            }
        ),
        firstStage = RocketStage(
            engines = firstStage.engines,
            fuelAmountTons = firstStage.fuelAmountTons,
            burnTimeSec = firstStage.burnTimeSec
        ),
        secondStage = RocketStage(
            engines = secondStage.engines,
            fuelAmountTons = secondStage.fuelAmountTons,
            burnTimeSec = secondStage.burnTimeSec
        ),
        flickrImage = flickrImages.first()
    )
}