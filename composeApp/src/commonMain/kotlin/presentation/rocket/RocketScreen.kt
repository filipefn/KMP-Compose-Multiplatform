package presentation.rocket

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Rocket
import domain.model.RocketParam
import domain.model.RocketStage
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.stringResource
import presentation.launch.RocketLaunchScreen
import spacex.composeapp.generated.resources.*

object RocketScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val modelScreen = koinScreenModel<RocketScreenModel>()
        val state by modelScreen.state.collectAsState()
        RocketLaunchView(state = state,
            retry = modelScreen::getAllRockets,
            goToLaunches = {
                navigator.push(RocketLaunchScreen(it))
            })
    }
}

@Composable
fun RocketLaunchView(
    state: RocketState,
    retry: () -> Unit,
    goToLaunches: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is RocketState.Loading -> {
                LoadingComponent()
            }
            is RocketState.Loaded -> {
                RocketsContent(
                    rockets = state.rockets,
                    onLaunchesClick = { goToLaunches(it) },
                )
            }
            is RocketState.Error -> {
                ErrorComponent(retry)
            }
        }
    }
}

@Composable
fun ErrorComponent(retry: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().clickable {
            retry()
        }
    ) {
        Text("DEU RUIM...Tente novamente", style = MaterialTheme.typography.bodyLarge)
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RocketsContent(
    rockets: List<Rocket>,
    onLaunchesClick: (rocketId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxSize()) {
        val pagerState = rememberPagerState(0) { rockets.size }
        HorizontalPager(
            state = pagerState,
            key = { index -> rockets[index].id },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            val rocket = rockets[page]
            RocketContent(
                rocket = rocket,
                onShowLaunchesClick = { onLaunchesClick(rocket.id) },
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            HorizontalPagerIndicator(
                pagerState = pagerState,
                pageCount = rockets.size,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 18.dp)
            )
        }
    }
}
@Composable
fun RocketContent(
    rocket: Rocket,
    onShowLaunchesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        KamelImage(
            resource = asyncPainterResource(rocket.flickrImage),
            contentDescription = "Loaded Image",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxWidth()
                .height(260.dp),
        )
        Column(
            modifier = Modifier
                .offset(y = ((-32).dp))
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 32.dp, top = 48.dp, end = 24.dp)
            ) {
                Text(
                    rocket.name,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.weight(1f)
                )
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 28.dp),
                modifier = Modifier.padding(top = 28.dp, bottom = 32.dp)
            ) {
                items(rocket.params) { param ->
                    RocketParamCard(param)
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                RocketInfoCell(
                    title = stringResource(Res.string.rocket_country),
                    value = rocket.country
                )
                RocketInfoCell(
                    title = stringResource(Res.string.rocket_cost_per_launch),
                    value = rocket.costPerLaunch.toString()
                )
            }
            RocketStageCell(
                title = stringResource(Res.string.rocket_stage_first),
                stage = rocket.firstStage,
                modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 40.dp)
            )
            RocketStageCell(
                title = stringResource(Res.string.rocket_stage_second),
                stage = rocket.secondStage,
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 40.dp)
            )
            AppButton(
                onClick = onShowLaunchesClick,
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                Text(
                    text = stringResource(Res.string.rocket_show_launches_btn),
                    style = MaterialTheme.typography.bodyLarge.copy(Color.Black)
                )
            }
        }
    }
}

@Composable
private fun RocketParamCard(
    param: RocketParam,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .size(96.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(param.value, style = MaterialTheme.typography.labelMedium)
        Text(
            param.title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
private fun RocketInfoCell(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.tertiary,
            maxLines = 1,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.End,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
private fun RocketStageCell(
    title: String,
    stage: RocketStage,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.labelMedium
        )
        RocketStageInfoCell(
            title = stringResource(Res.string.rocket_stage_engines_count),
            value = stage.engines.toString(),
        )
        RocketStageInfoCell(
            title = stringResource(Res.string.rocket_stage_fuel_amount),
            value = stage.fuelAmountTons.toString(),
            unit = stringResource(Res.string.rocket_stage_fuel_amount_unit),
        )
        RocketStageInfoCell(
            title = stringResource(Res.string.rocket_stage_burn_time),
            value = stage.burnTimeSec.toString(),
            unit = stringResource(Res.string.rocket_stage_burn_time_unit),
        )
    }
}

@Composable
private fun RocketStageInfoCell(
    title: String,
    value: String,
    unit: String = "",
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.tertiary,
            maxLines = 1,
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = unit,
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}

@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            MaterialTheme.colorScheme.primaryContainer
        ),
        shape = RoundedCornerShape(12.dp),
        content = content
    )
}