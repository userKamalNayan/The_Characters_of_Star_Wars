package com.kamalnayan.domain.domain.repository

import com.kamalnayan.domain.domain.models.character.CharactersResponse
import com.kamalnayan.domain.domain.models.film.FilmResponse
import com.skydoves.sandwich.ApiResponse

/** @Author Kamal Nayan
Created on: 09/01/24
 **/
interface ICharactersRepository {
    suspend fun getCharacters(): ApiResponse<CharactersResponse>

    suspend fun getFilm(filmUrl: String): ApiResponse<FilmResponse>
}