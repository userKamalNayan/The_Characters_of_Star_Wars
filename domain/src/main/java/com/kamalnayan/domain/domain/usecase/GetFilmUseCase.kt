package com.kamalnayan.domain.domain.usecase

import com.kamalnayan.domain.domain.base.usecase.BaseUseCase
import com.kamalnayan.domain.domain.models.film.FilmResponse
import com.kamalnayan.domain.domain.repository.ICharactersRepository
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

/** @Author Kamal Nayan
Created on: 10/01/24
 **/
class GetFilmUseCase @Inject constructor(private val repository: ICharactersRepository) :
    BaseUseCase<String, FilmResponse?>() {
    override suspend fun invoke(filmUrl: String?): FilmResponse? {
        return repository.getFilm(filmUrl.orEmpty())
    }
}