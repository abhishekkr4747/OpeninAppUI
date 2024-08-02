package com.example.openinapp.network

import com.example.openinapp.model.LinkResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("api/v1/dashboardNew")
    suspend fun getData(@Header("Authorization") authorization: String) : Response<LinkResponse>
}