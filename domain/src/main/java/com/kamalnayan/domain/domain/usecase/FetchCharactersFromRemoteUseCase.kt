package com.kamalnayan.domain.domain.usecase

import com.kamalnayan.domain.domain.base.usecase.BaseUseCase
import com.kamalnayan.domain.domain.models.character.CharactersResponse
import com.kamalnayan.domain.domain.repository.ICharactersRepository
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

/** @Author Kamal Nayan
Created on: 11/01/24
 **/
class FetchCharactersFromRemoteUseCase @Inject constructor(private val repository: ICharactersRepository) :
    BaseUseCase<Int, ApiResponse<CharactersResponse>>() {
    override suspend fun invoke(params: Int?): ApiResponse<CharactersResponse> {
        return repository.fetchCharactersFromRemote(params ?: 1)
    }
}