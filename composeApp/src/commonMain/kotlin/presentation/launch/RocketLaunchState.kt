package presentation.launch

import domain.model.RocketLaunch

sealed class RocketLaunchState {
    data object Loading: RocketLaunchState()
    data object Error: RocketLaunchState()
    data class Loaded(
        val launches: List<RocketLaunch>
    ): RocketLaunchState()
}