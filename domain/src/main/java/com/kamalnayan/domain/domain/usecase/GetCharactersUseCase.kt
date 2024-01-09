package com.kamalnayan.domain.domain.usecase

import com.kamalnayan.domain.domain.base.usecase.BaseUseCase
import com.kamalnayan.domain.domain.character.CharactersResponse
import com.kamalnayan.domain.domain.repository.ICharactersRepository
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

/** @Author Kamal Nayan
Created on: 09/01/24
 **/
class GetCharactersUseCase @Inject constructor(private val repository: ICharactersRepository) :
    BaseUseCase<Unit, ApiResponse<CharactersResponse>>() {
    override suspend fun invoke(params: Unit?): ApiResponse<CharactersResponse> {
        return repository.getCharacters()
    }

}