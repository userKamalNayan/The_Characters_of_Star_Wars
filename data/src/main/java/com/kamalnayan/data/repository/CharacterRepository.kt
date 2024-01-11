package com.kamalnayan.data.repository

import com.kamalnayan.data.api.ApiService
import com.kamalnayan.data.db.dao.CharactersDao
import com.kamalnayan.domain.domain.models.character.CharacterItem
import com.kamalnayan.domain.domain.models.character.CharactersResponse
import com.kamalnayan.domain.domain.models.film.FilmResponse
import com.kamalnayan.domain.domain.repository.ICharactersRepository
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/** @Author Kamal Nayan
Created on: 09/01/24
 **/
class CharacterRepository @Inject constructor(
    private val apiService: ApiService,
    private val dao: CharactersDao
) : ICharactersRepository {

    override suspend fun getCharacters(): Flow<List<CharacterItem>> {
        return dao.getCharacters()
    }

    override suspend fun getFilm(filmUrl: String): ApiResponse<FilmResponse> {
        return apiService.getFilm(filmUrl)
    }

    override suspend fun fetchCharactersFromRemote(page: Int): ApiResponse<CharactersResponse> {
        val response = apiService.getCharacters(page)
        response.suspendOnSuccess {
            withContext(Dispatchers.IO) {
                dao.insertCharacters(this@suspendOnSuccess.data.characterItems)
            }
        }
        return response
    }
}