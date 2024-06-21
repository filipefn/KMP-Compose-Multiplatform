package presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import domain.model.RocketLaunch

object RocketLaunchScreen : Screen {

    @Composable
    override fun Content() {
        val modelScreen = koinScreenModel<RocketLaunchScreenModel>()
        val state by modelScreen.state.collectAsState()
        RocketLaunchView(state = state)
    }
}

@Composable
fun RocketLaunchView(state: RocketLaunchState) {

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "SpaceX Launches", style = MaterialTheme.typography.h2)
            })
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            when (state) {
                is RocketLaunchState.Error -> ErrorComponent()
                is RocketLaunchState.Loading -> LoadingComponent()
                is RocketLaunchState.Loaded -> LoadedComponent(state.launches)
            }
        }
    }

}

@Composable
fun ErrorComponent() {
    Text(text = "DEU RUIM", style = MaterialTheme.typography.h3.copy(color = Color.Red))
}

@Composable
fun LoadingComponent() {
    Text(text = "Aguarde", style = MaterialTheme.typography.h3.copy(color = Color.Magenta))
}

@Composable
fun LoadedComponent(launches: List<RocketLaunch>) {
    LazyColumn {
        items(launches) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "${it.missionName} - ${it.flightNumber}",
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.height(8.dp))

                val (textSuccess, color) =
                    if (it.launchSuccess) "Nill Astrong" to Color.Green
                    else "Cabum" to Color.Red

                Text(text = textSuccess, style = MaterialTheme.typography.h3.copy(color = color))
            }

            Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Gray))
        }
    }
}