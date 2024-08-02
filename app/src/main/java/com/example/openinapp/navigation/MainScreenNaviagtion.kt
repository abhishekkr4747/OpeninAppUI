package com.example.openinapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.openinapp.presentation.screens.CampaignsScreen
import com.example.openinapp.presentation.screens.CoursesScreen
import com.example.openinapp.presentation.screens.DashboardScreen
import com.example.openinapp.presentation.screens.ProfileScreen

@Composable
fun MainScreenNavigation(navController: NavHostController) {

    NavHost(navController, startDestination = Screen.Links.route!!) {

        //links
        composable(Screen.Links.route) {
            DashboardScreen()
        }

        //Courses
        composable(Screen.Courses.route!!) {
            CoursesScreen()
        }

        //Add
        composable(Screen.Add.route!!){
            // Add()
        }

        //Campaigns
        composable(Screen.Campaigns.route!!) {
            CampaignsScreen()
        }

        //Profile
        composable(Screen.Profile.route!!) {
            ProfileScreen()
        }
    }
}