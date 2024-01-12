package com.kamalnayan.data.repository

import android.util.Log
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
import kotlinx.coroutines.flow.onEach
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

    /**
     * Fetches Characters data from local db
     * if list is empty then we make the api call for first
     * page of data.
     */
    override suspend fun getCharacters(): Flow<List<CharacterItem>> {
        Log.d("repo-decide", "getCharacters: fetching data locally")
        return dao.getCharacters().onEach {
            if (it.isNullOrEmpty()) {
                fetchCharactersFromRemote(1)
            }
        }
    }

    /**
     * We first fetch data from local db, if it is null then
     * we fetch it from remote and store is locally, and
     * return the result.
     */
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


    /**
     * Fetches characters data from remote and inserts it to local db
     */
    override suspend fun fetchCharactersFromRemote(page: Int): ApiResponse<CharactersResponse> {
        Log.d("repo-decide", "getCharacters: making api call ")
        val response = apiService.getCharacters(page)
        response.suspendOnSuccess {
            withContext(Dispatchers.IO) {
                Log.d("repo-decide", "getCharacters: api call success")
                dao.insertCharacters(this@suspendOnSuccess.data.characterItems)
            }
        }
        return response
    }
}