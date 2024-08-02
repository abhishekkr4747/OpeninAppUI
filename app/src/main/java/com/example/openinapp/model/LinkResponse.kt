package com.example.openinapp.model

import com.google.gson.annotations.SerializedName

data class LinkResponse(
    val status: Boolean,
    val statusCode: Long,
    val message: String,
    @SerializedName("support_whatsapp_number")
    val supportWhatsappNumber: String,
    @SerializedName("extra_income")
    val extraIncome: Double,
    @SerializedName("total_links")
    val totalLinks: Long,
    @SerializedName("total_clicks")
    val totalClicks: Long,
    @SerializedName("today_clicks")
    val todayClicks: Long,
    @SerializedName("top_source")
    val topSource: String,
    @SerializedName("top_location")
    val topLocation: String,
    val startTime: String,
    @SerializedName("links_created_today")
    val linksCreatedToday: Long,
    @SerializedName("applied_campaign")
    val appliedCampaign: Long,
    val data: Data
)

data class Data(
    @SerializedName("recent_links")
    val recentLinks: List<RecentLink>,
    @SerializedName("top_links")
    val topLinks: List<TopLink>,
    @SerializedName("favourite_links")
    val favouriteLinks: List<Any?>,
    @SerializedName("overall_url_chart")
    val overallUrlChart: Any?
)

data class RecentLink(
    @SerializedName("url_id")
    val urlId: Long,
    @SerializedName("web_link")
    val webLink: String,
    @SerializedName("smart_link")
    val smartLink: String,
    val title: String,
    @SerializedName("total_clicks")
    val totalClicks: Long,
    @SerializedName("original_image")
    val originalImage: String,
    val thumbnail: Any?,
    @SerializedName("times_ago")
    val timesAgo: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("domain_id")
    val domainId: String,
    @SerializedName("url_prefix")
    val urlPrefix: Any?,
    @SerializedName("url_suffix")
    val urlSuffix: String,
    val app: String,
    @SerializedName("is_favourite")
    val isFavourite: Boolean,
)

data class TopLink(
    @SerializedName("url_id")
    val urlId: Long,
    @SerializedName("web_link")
    val webLink: String,
    @SerializedName("smart_link")
    val smartLink: String,
    val title: String,
    @SerializedName("total_clicks")
    val totalClicks: Long,
    @SerializedName("original_image")
    val originalImage: String,
    val thumbnail: Any?,
    @SerializedName("times_ago")
    val timesAgo: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("domain_id")
    val domainId: String,
    @SerializedName("url_prefix")
    val urlPrefix: String?,
    @SerializedName("url_suffix")
    val urlSuffix: String,
    val app: String,
    @SerializedName("is_favourite")
    val isFavourite: Boolean,
)
