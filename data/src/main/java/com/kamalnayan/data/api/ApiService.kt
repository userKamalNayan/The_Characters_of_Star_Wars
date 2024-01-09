package com.kamalnayan.data.api

import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

/** @Author Kamal Nayan
Created on: 09/01/24
 **/
interface ApiService {
    @GET("people/")
    suspend fun getCharacters()//:ApiResponse<>
}