package com.kamalnayan.domain.domain.usecase

import com.kamalnayan.domain.domain.base.usecase.BaseUseCase
import com.kamalnayan.domain.domain.models.film.FilmResponse
import com.kamalnayan.domain.domain.repository.ICharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/** @Author Kamal Nayan
* Created on: 10/01/24
 *
 * Returns data from Local DB if available otherwise
 * fetches data from remote and returns.
 **/
class GetFilmUseCase @Inject constructor(private val repository: ICharactersRepository) :
    BaseUseCase<String, FilmResponse?>() {
    override suspend fun invoke(filmUrl: String?): FilmResponse? {
        return withContext(Dispatchers.IO) { repository.getFilm(filmUrl.orEmpty()) }
    }
}