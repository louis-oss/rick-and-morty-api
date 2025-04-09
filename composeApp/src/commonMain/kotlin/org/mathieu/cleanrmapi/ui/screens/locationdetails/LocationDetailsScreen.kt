package org.mathieu.cleanrmapi.ui.screens.locationdetails

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.mathieu.cleanrmapi.ui.core.composables.BackArrow
import org.mathieu.cleanrmapi.ui.core.composables.CharacterCard
import org.mathieu.cleanrmapi.ui.core.composables.Screen
import org.mathieu.cleanrmapi.ui.core.theme.PrimaryColor

@Composable
fun LocationDetailsScreen(
    navController: NavController,
    id: Int
) {
    Screen(
        viewModel = viewModel { LocationDetailsViewModel() },
        navController = navController
    ) { state, viewModel ->

        LaunchedEffect(key1 = id) {
            viewModel.init(locationId = id)
        }

        LocationDetailsContent(
            state = state,
            onClickBack = navController::popBackStack,
            onAction = viewModel::handleAction
        )
    }
}

@Composable
private fun LocationDetailsContent(
    state: LocationDetailsState,
    onAction: (LocationDetailsAction) -> Unit,
    onClickBack: () -> Unit
) = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    BackArrow(
        modifier = Modifier
            .align(Alignment.TopStart),
        onClick = onClickBack
    )

    Crossfade(targetState = state) {
        when (it) {
            is LocationDetailsState.Error -> LocationDetailsError(it.message)
            is LocationDetailsState.Loaded -> LocationDetailsLoadedContent(it, onAction)
            LocationDetailsState.Loading -> { /* Optionnel : spinner de chargement */ }
        }
    }
}

@Composable
private fun LocationDetailsError(error: String) {
    Text(
        text = error,
        fontSize = 20.sp,
        color = PrimaryColor,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
private fun LocationDetailsLoadedContent(
    state: LocationDetailsState.Loaded,
    onAction: (LocationDetailsAction) -> Unit
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(state.characters) { character ->
            CharacterCard(
                modifier = Modifier
                    .padding(8.dp),
                character = character
            )
        }
    }
}
