package com.example.openinapp.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.openinapp.navigation.Screen
import com.example.openinapp.ui.theme.figtree_medium

@Composable
fun BottomNav(navController: NavController) {

    val items = listOf(
        Screen.Links,
        Screen.Courses,
        Screen.Campaigns,
        Screen.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier.height(100.dp),
        containerColor = Color.White
    ) {
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    screen.icon?.let { iconRes ->
                        Icon(
                            imageVector = ImageVector.vectorResource(id = iconRes),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = if (currentRoute == screen.route) Color.Black else Color.Gray,
                        )
                    }
                },
                label = {
                    screen.title?.let { title ->
                        Text(
                            text = title,
                            color = if (currentRoute == screen.route) Color.Black else Color.Gray,
                            fontSize = 11.sp,
                            fontFamily = figtree_medium
                        )
                    }
                },
                selected = currentRoute == screen.route,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.Black,
                    unselectedTextColor = Color.Gray
                ),
                onClick = {
                    screen.route?.let { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
