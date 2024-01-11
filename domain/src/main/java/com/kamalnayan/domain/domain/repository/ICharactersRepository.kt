package com.kamalnayan.domain.domain.repository

import com.kamalnayan.domain.domain.models.character.CharacterItem
import com.kamalnayan.domain.domain.models.character.CharactersResponse
import com.kamalnayan.domain.domain.models.film.FilmResponse
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.flow.Flow

/** @Author Kamal Nayan
Created on: 09/01/24
 **/
interface ICharactersRepository {
    suspend fun getCharacters(): Flow<List<CharacterItem>>

    suspend fun getFilm(filmUrl: String): ApiResponse<FilmResponse>

    suspend fun fetchCharactersFromRemote(page: Int): ApiResponse<CharactersResponse>
}