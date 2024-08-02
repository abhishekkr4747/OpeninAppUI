package com.example.openinapp.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openinapp.model.LinkResponse
import com.example.openinapp.network.ApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel: ViewModel() {
    var linkApiResponseState = mutableStateOf<LinkResponse?>(null)


    val BASE_URL = "https://api.inopenapp.com/"
    val token = "Bearer " +
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI"
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    fun getApiData() {
        viewModelScope.launch {
            try {
                val response = apiService.getData(token)

                if (response.isSuccessful) {
                    linkApiResponseState.value = response.body()
                    Log.d("TAG", "success: ${response.body()}")
                } else {
                    response.errorBody()?.let { errorBody ->
                        val errorContent = errorBody.string()
                        Log.d("TAG", "Error: $errorContent")
                    }
                }
            } catch (e: Exception) {
                Log.d("TAG", "Exception: ${e.message}")
            }

        }
    }

}