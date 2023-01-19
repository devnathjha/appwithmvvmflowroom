package com.app.testexample.data.api

import com.app.testexample.data.model.Medicine
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("2828cbe7-505a-4399-bb67-ba4026a052a3")
    suspend fun getMedicine(): Response<Medicine>
}
