package domain

import domain.model.RocketLaunch

interface LaunchesRepository {
    suspend fun getAllLaunches(forceReload: Boolean, rocketId: String): List<RocketLaunch>
}