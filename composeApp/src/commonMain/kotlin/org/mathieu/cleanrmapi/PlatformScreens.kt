package org.mathieu.cleanrmapi

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
expect fun getCharacterDetailsScreen(navController: NavController, id: Int)