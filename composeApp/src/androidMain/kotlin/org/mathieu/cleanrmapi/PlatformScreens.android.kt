package org.mathieu.cleanrmapi

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.mathieu.cleanrmapi.ui.CharacterDetailsScreen

@Composable
actual fun getCharacterDetailsScreen(navController: NavController, id: Int) {
    CharacterDetailsScreen(navController = navController, id = id)
}