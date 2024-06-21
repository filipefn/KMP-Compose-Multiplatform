package di

import org.koin.dsl.module
import presentation.RocketLaunchScreenModel

actual val screenModelModule = module {
    factory { RocketLaunchScreenModel(get()) }
}
