package com.example.openinapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.openinapp.R
import com.example.openinapp.ui.theme.figtree_semibold

@Composable
fun DashboardSection() {
    Row(
        modifier = Modifier
            .padding(top = 25.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Dashboard",
            fontSize = 24.sp,
            color = Color.White,
            fontFamily = figtree_semibold
        )

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_setting),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}