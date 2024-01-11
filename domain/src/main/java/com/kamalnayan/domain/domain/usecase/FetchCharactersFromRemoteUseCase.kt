package com.kamalnayan.domain.domain.usecase

import com.kamalnayan.domain.domain.base.usecase.BaseUseCase
import com.kamalnayan.domain.domain.models.character.CharactersResponse
import com.kamalnayan.domain.domain.repository.ICharactersRepository
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/** @Author Kamal Nayan
Created on: 11/01/24
 *
 * Fetches character data from remote.
 * @param pageNumber : page number of which data to fetch
 **/
class FetchCharactersFromRemoteUseCase @Inject constructor(private val repository: ICharactersRepository) :
    BaseUseCase<Int, ApiResponse<CharactersResponse>>() {
    override suspend fun invoke(params: Int?): ApiResponse<CharactersResponse> {
        return withContext(Dispatchers.IO) { repository.fetchCharactersFromRemote(params ?: 1) }
    }
}