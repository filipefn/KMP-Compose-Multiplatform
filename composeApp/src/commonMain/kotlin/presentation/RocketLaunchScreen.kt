package presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import domain.model.RocketLaunch

object RocketLaunchScreen : Screen {

    @Composable
    override fun Content() {
        val modelScreen = koinScreenModel<RocketLaunchScreenModel>()
        val state by modelScreen.state.collectAsState()
        RocketLaunchView(state = state, modelScreen::getAllLaunches)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RocketLaunchView(
    state: RocketLaunchState,
    onRefresh: (Boolean) -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()
    if (pullToRefreshState.isRefreshing) {
        onRefresh(true)
        pullToRefreshState.endRefresh()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    "SpaceX Launches",
                    style = MaterialTheme.typography.headlineLarge
                )
            })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .nestedScroll(pullToRefreshState.nestedScrollConnection)
                .fillMaxSize()
                .padding(padding)
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
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text("DEU RUIM...", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun LoadingComponent() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Loading...", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun LoadedComponent(launches: List<RocketLaunch>) {
    LazyColumn {
        items(launches) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "${it.missionName} - ${it.flightNumber}",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))

                val (textSuccess, color) =
                    if (it.launchSuccess) "Nill Astrong" to Color.Green
                    else "Cabum" to Color.Red

                Text(text = textSuccess, style = MaterialTheme.typography.bodyMedium.copy(color = color))

                if (it.details?.isNotBlank() == true) {
                    Text(
                        text = it.details
                    )
                }
            }

            Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Gray))
        }
    }
}