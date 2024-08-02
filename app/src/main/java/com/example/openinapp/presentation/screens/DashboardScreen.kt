package com.example.openinapp.presentation.screens

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.openinapp.R
import com.example.openinapp.model.LinkResponse
import com.example.openinapp.model.RecentLink
import com.example.openinapp.model.TopLink
import com.example.openinapp.presentation.MainViewModel
import com.example.openinapp.ui.theme.figtree_bold
import com.example.openinapp.ui.theme.figtree_medium
import com.example.openinapp.ui.theme.figtree_regular
import com.example.openinapp.ui.theme.figtree_semibold
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Locale


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun DashboardScreen() {
    val viewModel: MainViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.getApiData()
    }

    val response by viewModel.linkApiResponseState
    val context = LocalContext.current

    Scaffold(
        containerColor = Color(0xFF0E6FFF)
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            DashboardSection()

            Box(
                modifier = Modifier
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 16.dp, topEnd = 16.dp
                        )
                    )
                    .background(color = Color(0xFFF5F5F5))
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 24.dp, bottom = 24.dp)
                ) {
                    GreetingsSection()

                    Spacer(modifier = Modifier.height(24.dp))

                    // Chart
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OverviewAndDate()

                            val xData = listOf(0f, 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f)
                            val yData = listOf(20f, 30f, 50f, 60f, 90f, 70f, 40f, 80f, 60f, 100f, 75f)
                            val dataLabel = "Sample Data"

                            ChartGraph(xData = xData, yData = yData, dataLabel = dataLabel)
                        }

                    }

                    // Clicks, Location, Source
                    ClickLocationSocial(response)
                    
                    // View Analytics Button
                    ViewButton(icon = R.drawable.ic_analytics, title = "View Analytics")

                    Spacer(modifier = Modifier.height(24.dp))

                    var selected by remember { mutableStateOf("Top Links") }
                    var showTopLinks by remember { mutableStateOf(true) }
                    val topLinks = response?.data?.topLinks
                    val recentLinks = response?.data?.recentLinks

                    // Links
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Chip(
                            title = "Top Links",
                            selected = selected,
                            onSelected = {
                                selected = "Top Links"
                                showTopLinks = true
                            }
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Chip(
                            title = "Recent Links",
                            selected = selected,
                            onSelected = {
                                selected = "Recent Links"
                                showTopLinks = false
                            }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_search), contentDescription = null, tint = Color.Unspecified)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (showTopLinks) {
                            topLinks?.let {
                                    it.forEach { topLink ->
                                        TopLinksItem(link = topLink,
                                            onClick = {
                                                val clipboardManager =
                                                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                                val clipData = ClipData.newPlainText("Link", topLink.webLink)
                                                clipboardManager.setPrimaryClip(clipData)
                                                Toast.makeText(
                                                    context,
                                                    "Copy to clipboard",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        )
                                    }
                            }
                        } else {
                            recentLinks?.let {
                                    it.forEach { recentLink ->
                                        RecentLinksItem(link = recentLink,
                                            onClick = {
                                            val clipboardManager =
                                                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                            val clipData = ClipData.newPlainText("Link", recentLink.webLink)
                                            clipboardManager.setPrimaryClip(clipData)
                                                Toast.makeText(
                                                    context,
                                                    "Copy to clipboard",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                        }
                                        )
                                    }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // View All Links Button
                    ViewButton(icon = R.drawable.ic_link, title = "View all Links")

                    Spacer(modifier = Modifier.height(24.dp))

                    // Talk with us
                    TalkAndFAQCard(
                        borderColor = Color(0xFFb0e6b7),
                        containerColor = Color(0xFFe0f0e2),
                        icon = R.drawable.ic_whatsapp_logo,
                        title = "Talk with us"
                        ) {
                        val whatsappNumber = response?.supportWhatsappNumber
                        val message = "Hello"

                        val uri = Uri.parse(
                            "https://api.whatsapp.com/send?phone=$whatsappNumber&text=${
                                URLEncoder.encode(message, "UTF-8")
                            }"
                        )
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Frequently Asked Questions
                    TalkAndFAQCard(
                        borderColor = Color(0xFFa1c7ff),
                        containerColor = Color(0xFFe8f1ff),
                        icon = R.drawable.ic_faq_logo,
                        title = "Frequently Asked Questions"
                    ){
                        val faqUrl = "https://openinapp.com/faq"
                        val uri = Uri.parse(faqUrl)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)
                    }
                }
            }
        }

    }

}



@Composable
private fun OverviewAndDate() {
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

@Composable
private fun TalkAndFAQCard(
    borderColor: Color,
    containerColor: Color,
    icon: Int,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        border = BorderStroke(1.dp, borderColor),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = icon),
                contentDescription = "Whatsapp",
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(end = 16.dp)
            )

            Text(
                text = title,
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontFamily = figtree_medium
            )
        }
    }
}


@Composable
private fun ViewButton(icon: Int, title: String) {
    Button(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color(0xFFD8D8D8)),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F5)),
        contentPadding = PaddingValues(top = 12.dp, bottom = 12.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = title,
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            color = Color.Black,
            fontSize = 16.sp,
            fontFamily = figtree_medium,
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun ChartGraph(
    xData: List<Float>,
    yData: List<Float>,
    dataLabel: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        factory = { context ->
            val chart = LineChart(context)

            val entries: List<Entry> = xData.zip(yData) { x, y -> Entry(x, y) }
            val dataSet = LineDataSet(entries, dataLabel).apply {
                color = Color(0xFF0E6FFF).toArgb()
                setDrawCircles(false)
                setDrawValues(false)
                lineWidth = 2f
                fillColor = Color(0xFF0E6FFF).toArgb()
                setDrawFilled(true)
                fillAlpha = 30
            }

            chart.apply {
                data = LineData(dataSet)
                setTouchEnabled(true)
                isDragEnabled = true
                isScaleXEnabled = true
                isScaleYEnabled = false



                description.isEnabled = false
                legend.isEnabled = false

                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    textColor = Color(0xFF999CA0).toArgb()
                    setDrawGridLines(true)
                    gridColor = Color(0xFF999CA0).toArgb()
                    valueFormatter = object : ValueFormatter() {
                        private val months = arrayOf(
                            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
                        )
                        override fun getFormattedValue(value: Float): String {
                            return months.getOrElse(value.toInt()) { value.toString() }
                        }
                    }
                }

                axisRight.apply {
                    setDrawAxisLine(false)
                    setDrawGridLines(false)
                    isEnabled = false
                }

                axisLeft.apply {
                    setDrawAxisLine(false)
                    setDrawGridLines(true)
                    textColor = Color(0xFF999CA0).toArgb()
                    gridColor = Color(0xFF999CA0).toArgb()
                }

            }

            chart.invalidate()
            chart
        }
    )
}


@Composable
private fun ClickLocationSocial(response: LinkResponse?) {
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

@Composable
private fun GreetingsSection() {
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

@Composable
private fun DashboardSection() {
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


@Composable
fun StatCard(icon: Int, value: String?, title: String) {
    Card(
        modifier = Modifier
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, top = 10.dp, bottom = 16.dp, end = 16.dp)
                .width(120.dp)
                .height(120.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = icon),
                contentDescription = "Icon",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (value != null) {
                Text(
                    text = value,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    fontFamily = figtree_medium
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color(0xFF999CA0),
                maxLines = 1,
                fontFamily = figtree_medium
            )
        }
    }
}


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

@Composable
fun TopLinksItem(
    link: TopLink,
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

            val stroke = Stroke(width = 3f,
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

@Composable
fun RecentLinksItem(
    link: RecentLink,
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

            val stroke = Stroke(width = 3f,
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

fun String.formatDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    val date = inputFormat.parse(this)
    return date?.let { outputFormat.format(it) } ?: this
}


