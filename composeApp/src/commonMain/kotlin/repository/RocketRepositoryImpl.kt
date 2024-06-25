package repository

import data.Database
import data.DatabaseDriverFactory
import domain.LaunchesRepository
import domain.RocketRepository
import domain.model.Rocket
import domain.model.RocketLaunch
import network.SpaceXApi
import network.model.toDomain

class RocketRepositoryImpl(
    val spaceXApi: SpaceXApi
) : RocketRepository {
    override suspend fun getAllRockets(): List<Rocket> {
        return spaceXApi.getAllRockets().map { it.toDomain() }
    }
}