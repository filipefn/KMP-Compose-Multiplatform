package presentation.launch

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.LaunchesRepository
import kotlinx.coroutines.launch

class RocketLaunchScreenModel(
    private val repository: LaunchesRepository
) : StateScreenModel<RocketLaunchState>(RocketLaunchState.Loading) {

    fun getAllLaunches(isForceReload: Boolean, rocketId: String) {
        screenModelScope.launch {
            mutableState.value = RocketLaunchState.Loading
            try {
                val launches = repository.getAllLaunches(isForceReload, rocketId)
                mutableState.value = RocketLaunchState.Loaded(launches)
            } catch (e: Exception) {
                mutableState.value = RocketLaunchState.Error
            }
        }
    }
}