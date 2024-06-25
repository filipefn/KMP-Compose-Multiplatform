package domain

import domain.model.Rocket
import domain.model.RocketLaunch

interface RocketRepository {
    suspend fun getAllRockets(): List<Rocket>
}