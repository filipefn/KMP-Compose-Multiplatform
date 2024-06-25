package presentation

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.LaunchesRepository
import kotlinx.coroutines.launch

class RocketLaunchScreenModel(
    private val repository: LaunchesRepository
) : StateScreenModel<RocketLaunchState>(RocketLaunchState.Loading) {

    init {
        getAllLaunches(false)
    }

    fun getAllLaunches(isForceReload: Boolean) {
        screenModelScope.launch {
            mutableState.value = RocketLaunchState.Loading
            try {
                val launches = repository.getAllLaunches(isForceReload)
                mutableState.value = RocketLaunchState.Loaded(launches)
            } catch (e: Exception) {
                mutableState.value = RocketLaunchState.Error
            }
        }
    }
}