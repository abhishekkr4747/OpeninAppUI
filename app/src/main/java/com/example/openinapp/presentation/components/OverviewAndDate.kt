package com.example.openinapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.openinapp.ui.theme.figtree_medium
import com.example.openinapp.ui.theme.figtree_semibold

@Composable
fun OverviewAndDate() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Overview",
            fontSize = 14.sp,
            color = Color(0xFF999CA0),
            fontFamily = figtree_medium
        )

        Card(
            shape = RoundedCornerShape(6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFEBEBEB))
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "22 Jan - 23 Nov",
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontFamily = figtree_semibold,
                    modifier = Modifier
                        .padding(end = 4.dp)
                )

                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_time),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
    }
}