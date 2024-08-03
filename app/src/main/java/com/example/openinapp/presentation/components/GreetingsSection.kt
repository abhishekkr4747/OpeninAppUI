package com.example.openinapp.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.openinapp.ui.theme.figtree_bold
import com.example.openinapp.ui.theme.figtree_regular

@Composable
fun GreetingsSection() {
    Text(
        text = "Good morning",
        fontSize = 16.sp,
        color = Color(0xFF999CA0),
        fontFamily = figtree_regular
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = "Abhishek Kumar \uD83D\uDC4B",
        fontSize = 24.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        fontFamily = figtree_bold
    )
}