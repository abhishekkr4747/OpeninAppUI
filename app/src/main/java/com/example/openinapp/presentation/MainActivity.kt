package com.example.openinapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.openinapp.navigation.MainScreenNavigation
import com.example.openinapp.presentation.components.BottomNav
import com.example.openinapp.ui.theme.OpeninAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpeninAppTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(color = Color(0xFF0E6FFF))
               BottomBarWithFabDem()
            }
        }

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BottomBarWithFabDem() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(65.dp),
                containerColor = Color.White,
                tonalElevation = 22.dp
            ) {
                BottomNav(navController = navController)
            }
        },
        contentWindowInsets = WindowInsets(0,0,0,0)
    ) {
        Box(
            modifier = Modifier
                .padding(it)
        ) {
            MainScreenNavigation(navController)
        }
    }
}



