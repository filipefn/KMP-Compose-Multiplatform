package di

import domain.LaunchesRepository
import domain.RocketRepository
import network.SpaceXApi
import network.getHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import presentation.launch.RocketLaunchScreenModel
import presentation.rocket.RocketScreenModel
import repository.LaunchesRepositoryImpl
import repository.RocketRepositoryImpl

val networkModule = module {
    single { getHttpClient() }
}

val dataModule = module {
    single { SpaceXApi(httpClient = get()) }

    single<LaunchesRepository> { LaunchesRepositoryImpl(
        spaceXApi = get(),
        databaseDriverFactory = get()
    ) }

    single<RocketRepository> { RocketRepositoryImpl(spaceXApi = get()) }
}

val screenModelModule = module {
    factory { RocketLaunchScreenModel(get()) }
    factory { RocketScreenModel(get()) }
}
