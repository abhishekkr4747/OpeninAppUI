package com.example.openinapp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.openinapp.ui.theme.figtree_bold

@Composable
fun Chip(
    title: String,
    selected: String,
    onSelected: () -> Unit
) {
    val isSelected = title == selected

    val background = if (isSelected) Color(0xFF0E6FFF) else Color(0xFFF5F5F5)
    val contentColor = if (isSelected) Color.White else Color(0xFF999CA0)

    Box(
        modifier = Modifier
            .height(36.dp)
            .clip(CircleShape)
            .background(background)
            .clickable {
                onSelected()
            },
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = true) {

            Text(
                text = title,
                color = contentColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = figtree_bold,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
        }
    }
}