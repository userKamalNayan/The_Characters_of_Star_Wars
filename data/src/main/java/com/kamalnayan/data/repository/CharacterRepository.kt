package com.kamalnayan.data.repository

import com.kamalnayan.data.api.ApiService
import com.kamalnayan.data.db.dao.CharactersDao
import com.kamalnayan.domain.domain.models.character.CharacterItem
import com.kamalnayan.domain.domain.models.character.CharactersResponse
import com.kamalnayan.domain.domain.models.film.FilmResponse
import com.kamalnayan.domain.domain.repository.ICharactersRepository
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/** @Author Kamal Nayan
Created on: 09/01/24
 **/
class CharacterRepository @Inject constructor(
    private val apiService: ApiService, private val dao: CharactersDao
) : ICharactersRepository {

    override suspend fun getCharacters(): Flow<List<CharacterItem>> {
        return dao.getCharacters()
    }

    override suspend fun getFilm(filmUrl: String): FilmResponse? {
        return withContext(Dispatchers.IO) {
            val filmFromLocalDb = dao.getFilm(filmUrl)
            /**
             * Means data is not available locally, hence we are fetching it from remote
             * then inserting it to db, and return the response
             */
            if (filmFromLocalDb == null) {
               return@withContext suspendCoroutine<FilmResponse?> { continuation ->
                    this.launch {
                        apiService.getFilm(filmUrl).suspendOnSuccess {
                            dao.upsertFilm(this@suspendOnSuccess.data)
                            continuation.resume(this@suspendOnSuccess.data)
                        }.onError {
                            continuation.resume(null)
                        }
                    }
                }
            }

            return@withContext filmFromLocalDb
        }
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