package di

import domain.LaunchesRepository
import network.SpaceXApi
import network.getHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import presentation.RocketLaunchScreenModel
import repository.LaunchesRepositoryImpl

val networkModule = module {
    single { getHttpClient() }
}

val dataModule = module {
    single { SpaceXApi(httpClient = get()) }

    single<LaunchesRepository> { LaunchesRepositoryImpl(
        spaceXApi = get(),
        databaseDriverFactory = get()
    ) }
}

expect val screenModelModule: Module
