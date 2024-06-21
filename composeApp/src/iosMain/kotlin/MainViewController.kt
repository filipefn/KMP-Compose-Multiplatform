import androidx.compose.ui.window.ComposeUIViewController
import di.dataModule
import di.iosDataModule
import di.networkModule
import di.screenModelModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        startKoin {
            modules(iosDataModule, networkModule, dataModule, screenModelModule)
        }
    }
) { App() }