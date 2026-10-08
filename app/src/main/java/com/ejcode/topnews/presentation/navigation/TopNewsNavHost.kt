package com.ejcode.topnews.presentation.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ejcode.topnews.presentation.screens.browse.BrowseScreen
import com.ejcode.topnews.presentation.screens.details.DetailsScreen
import com.ejcode.topnews.presentation.screens.feed.FeedScreen
import com.ejcode.topnews.presentation.screens.saved.SavedScreen

const val ROUTE_FEED = "feed"
const val ROUTE_DETAILS = "details"
const val ROUTE_EXPLORE = "explore"
const val ROUTE_SAVED = "saved"
const val ARG_ARTICLE_ID = "articleId"

// Pattern used when declaring the destination: "details/{articleId}"
const val ROUTE_DETAILS_PATTERN = "$ROUTE_DETAILS/{$ARG_ARTICLE_ID}"

// Used when navigating. The ID is a full URL, so it must be encoded to fit in a path segment.
fun detailsRoute(articleId: String) = "$ROUTE_DETAILS/$articleId"
private data class TopLevelDestination(
    val route: String,
    val icon: ImageVector,
    val title: String,
)

// Destinations exposed to users on the main screen
private val topLevelDestinations = listOf(
    TopLevelDestination(ROUTE_FEED, Icons.AutoMirrored.Filled.List, "Feed"),
    TopLevelDestination(ROUTE_EXPLORE, Icons.Default.AccountCircle, "Explore"),
    TopLevelDestination(ROUTE_SAVED, Icons.Default.Favorite, "Saved")
)

@Composable
fun TopNewsNavHost(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Bottom bar only on the top-level tabs, not on Details.
    val showBottomBar = topLevelDestinations.any { it.route == currentRoute }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    topLevelDestinations.forEach { dest ->
                        NavigationBarItem(
                            selected = currentRoute == dest.route,
                            onClick = {
                                navController.navigate(dest.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(dest.icon, contentDescription = dest.title) },
                            label = { Text(dest.title) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ROUTE_FEED,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
        ) {
            // Define route and screen to display
            composable(ROUTE_FEED) {
                FeedScreen()
            }
            composable(ROUTE_EXPLORE) {
                BrowseScreen()
            }
            composable(ROUTE_SAVED) {
                SavedScreen()
            }
            composable(
                route = ROUTE_DETAILS_PATTERN,
                arguments = listOf(navArgument(ARG_ARTICLE_ID) { type = NavType.StringType })
            ) {
                DetailsScreen()
            }
        }
    }




}