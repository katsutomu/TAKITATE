package io.github.reservationbytom.service.repository

import io.github.reservationbytom.service.model.GNaviResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface GNaviService {
    @GET("RestSearchAPI/v3/")
    suspend fun getRestaurants(
        @Query("keyid") keyid: String,
        @Query("range") range: Int,
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double
    ): Call<GNaviResponse> // Response<GNaviResponse> ?
}