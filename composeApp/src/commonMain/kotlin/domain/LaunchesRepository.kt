package domain

import domain.model.RocketLaunch

interface LaunchesRepository {
    suspend fun getAllLaunches(forceReload: Boolean): List<RocketLaunch>
}