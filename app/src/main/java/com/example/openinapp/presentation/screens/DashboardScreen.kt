package com.example.openinapp.presentation.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.openinapp.R
import com.example.openinapp.presentation.MainViewModel
import com.example.openinapp.presentation.components.Chart
import com.example.openinapp.presentation.components.Chip
import com.example.openinapp.presentation.components.ClickLocationSocial
import com.example.openinapp.presentation.components.DashboardSection
import com.example.openinapp.presentation.components.GreetingsSection
import com.example.openinapp.presentation.components.LinksItem
import com.example.openinapp.presentation.components.TalkAndFAQCard
import com.example.openinapp.presentation.components.ViewButton
import com.example.openinapp.presentation.utils.CopyToClipboard
import java.net.URLEncoder


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
                    Chart()

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
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_search),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
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
                                    LinksItem(link = topLink,
                                        onClick = {
                                            CopyToClipboard(context, topLink)
                                        }
                                    )
                                }
                            }
                        } else {
                            recentLinks?.let {
                                it.forEach { recentLink ->
                                    LinksItem(link = recentLink,
                                        onClick = {
                                            CopyToClipboard(context, recentLink)
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
                    ) {
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





