package presentation.rocket

import domain.model.Rocket
import domain.model.RocketLaunch

sealed class RocketState {
    data object Loading: RocketState()
    data object Error: RocketState()
    data class Loaded(
        val rockets: List<Rocket>
    ): RocketState()
}