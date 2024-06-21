package presentation

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.LaunchesRepository
import kotlinx.coroutines.launch

class RocketLaunchScreenModel(
    private val repository: LaunchesRepository
) : StateScreenModel<RocketLaunchState>(RocketLaunchState.Loading) {

    init {
        getAllLaunches()
    }

    fun getAllLaunches() {
        screenModelScope.launch {
            mutableState.value = RocketLaunchState.Loading
            try {
                val launches = repository.getAllLaunches(false)
                mutableState.value = RocketLaunchState.Loaded(launches)
            } catch (e: Exception) {
                mutableState.value = RocketLaunchState.Error
            }
        }
    }
}