package presentation.rocket

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.LaunchesRepository
import domain.RocketRepository
import kotlinx.coroutines.launch

class RocketScreenModel(
    private val repository: RocketRepository
) : StateScreenModel<RocketState>(RocketState.Loading) {

    init {
        getAllRockets()
    }

    fun getAllRockets() {
        screenModelScope.launch {
            mutableState.value = RocketState.Loading
            try {
                val launches = repository.getAllRockets()
                mutableState.value = RocketState.Loaded(launches)
            } catch (e: Exception) {
                mutableState.value = RocketState.Error
            }
        }
    }
}