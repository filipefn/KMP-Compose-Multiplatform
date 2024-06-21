import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import presentation.RocketLaunchScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
       KoinContext {
           Navigator(RocketLaunchScreen)
       }
    }
}