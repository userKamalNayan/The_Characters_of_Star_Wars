package com.kamalnayan.domain.domain.repository

import com.kamalnayan.domain.domain.character.CharactersResponse
import com.skydoves.sandwich.ApiResponse

/** @Author Kamal Nayan
Created on: 09/01/24
 **/
interface ICharactersRepository{
  suspend fun getCharacters():ApiResponse<CharactersResponse>
}