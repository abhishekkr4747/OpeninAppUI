package com.example.openinapp.navigation


import com.example.openinapp.R

sealed class Screen(val route: String?, val title: String?, val icon: Int?) {

    object Links : Screen("link", "Links",  R.drawable.ic_link2)

    object Courses : Screen("courses", "Courses",  R.drawable.ic_courses)

    object Add: Screen("add",null,null)

    object Campaigns : Screen("campaigns", "Campaigns",  R.drawable.ic_campaign)

    object Profile : Screen("profile", "Profile",  R.drawable.ic_profile)
}