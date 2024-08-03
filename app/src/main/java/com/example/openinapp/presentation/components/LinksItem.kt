package com.example.openinapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.openinapp.R
import com.example.openinapp.model.Link
import com.example.openinapp.presentation.utils.formatDate
import com.example.openinapp.ui.theme.figtree_medium

@Composable
fun LinksItem(
    link: Link,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(link.originalImage)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .border(1.dp, color = Color(0xFFF5F5F5))
                        .size(48.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                )

                Column(
                    modifier = Modifier
                        .width(300.dp)
                        .padding(start = 16.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = link.title,
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontFamily = figtree_medium,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = link.createdAt.formatDate(),
                        color = Color(0xFF999CA0),
                        fontSize = 12.sp,
                        fontFamily = figtree_medium,
                        maxLines = 1
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = link.totalClicks.toString(),
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontFamily = figtree_medium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Clicks",
                        color = Color(0xFF999CA0),
                        fontSize = 12.sp,
                        fontFamily = figtree_medium,
                        maxLines = 1
                    )
                }
            }

            val stroke = Stroke(
                width = 3f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                    .background(color = Color(0xFFE8F1FF))
                    .drawBehind {
                        drawRoundRect(
                            color = Color(0xFFA6C7FF),
                            style = stroke
                        )
                    }

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = link.webLink,
                        fontSize = 14.sp,
                        color = Color(0xFF0E6FFF),
                        fontFamily = figtree_medium,
                        maxLines = 1,
                        modifier = Modifier
                            .width(300.dp)
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_copy),
                        contentDescription = "copy",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                onClick()
                            }
                    )
                }
            }
        }
    }
}