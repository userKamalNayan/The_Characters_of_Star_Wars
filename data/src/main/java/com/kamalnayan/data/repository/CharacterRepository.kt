package com.kamalnayan.data.repository

import com.kamalnayan.data.api.ApiService
import com.kamalnayan.domain.domain.models.character.CharactersResponse
import com.kamalnayan.domain.domain.models.film.FilmResponse
import com.kamalnayan.domain.domain.repository.ICharactersRepository
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

/** @Author Kamal Nayan
Created on: 09/01/24
 **/
class CharacterRepository @Inject constructor(private val apiService: ApiService) :
    ICharactersRepository {
    override suspend fun getCharacters(): ApiResponse<CharactersResponse> {
        return apiService.getCharacters()
    }

    override suspend fun getFilm(filmUrl: String): ApiResponse<FilmResponse> {
        return  apiService.getFilm(filmUrl)
    }
}