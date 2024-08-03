package com.example.openinapp.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.openinapp.R
import com.example.openinapp.model.LinkResponse

@Composable
fun ClickLocationSocial(response: LinkResponse?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        StatCard(
            icon = R.drawable.ic_click,
            value = "123",
            title = "Today's clicks"
        )
        StatCard(
            icon = R.drawable.ic_location,
            value = "Ahemdabad",
            title = "Top Location"
        )
        StatCard(
            icon = R.drawable.ic_social,
            value = "Instagram",
            title = "Top source"
        )
    }
}