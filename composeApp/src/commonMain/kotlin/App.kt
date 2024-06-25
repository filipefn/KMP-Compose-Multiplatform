import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import presentation.launch.RocketLaunchScreen
import presentation.rocket.RocketScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
       KoinContext {
           Navigator(RocketScreen)
       }
    }
}