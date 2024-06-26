package repository

import data.Database
import data.DatabaseDriverFactory
import domain.LaunchesRepository
import domain.model.RocketLaunch
import network.SpaceXApi
import network.model.toDomain

class LaunchesRepositoryImpl(
    val spaceXApi: SpaceXApi,
    databaseDriverFactory: DatabaseDriverFactory
) : LaunchesRepository {
    private val database = Database(databaseDriverFactory)

    override suspend fun getAllLaunches(
        forceReload: Boolean,
        rocketId: String
    ): List<RocketLaunch> {
        val cacheLaunches = database.getAllLaunches()

        return if (cacheLaunches.isNotEmpty() && forceReload.not()) {
            cacheLaunches
                .filter { it.rocketId == rocketId }
                .map { it.toDomain() }
        } else {
            spaceXApi.getAllLaunches().also {
                database.clearCache()
                database.insertLaunches(it)
            }.filter { it.rocketId == rocketId }
                .map { it.toDomain() }
        }
    }
}