package com.kamalnayan.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.kamalnayan.data.api.ApiService
import com.kamalnayan.domain.domain.models.character.CharacterItem
import com.kamalnayan.domain.domain.models.character.CharactersResponse
import com.kamalnayan.domain.domain.models.film.FilmResponse
import com.kamalnayan.domain.domain.repository.ICharactersRepository
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/** @Author Kamal Nayan
Created on: 09/01/24
 **/
class CharacterRepository @Inject constructor(
    private val apiService: ApiService,
    private val pager: Pager<Int, CharacterItem>
) : ICharactersRepository {
    override suspend fun getCharacters(): Flow<PagingData<CharacterItem>> {
        return pager.flow
    }

    override suspend fun getFilm(filmUrl: String): ApiResponse<FilmResponse> {
        return apiService.getFilm(filmUrl)
    }
}