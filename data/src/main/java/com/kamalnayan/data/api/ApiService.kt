package com.kamalnayan.data.api

import com.kamalnayan.domain.domain.models.character.CharactersResponse
import com.kamalnayan.domain.domain.models.film.FilmResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/** @Author Kamal Nayan
Created on: 09/01/24
 **/
interface ApiService {
    @GET("people/")
    suspend fun getCharacters(
        @Query("page")page:Int
    ): ApiResponse<CharactersResponse>

    @GET
    suspend fun getFilm(@Url filmUrl: String): ApiResponse<FilmResponse>
}