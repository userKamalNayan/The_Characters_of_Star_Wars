package com.kamalnayan.domain.domain.usecase

import androidx.paging.PagingData
import androidx.paging.filter
import com.kamalnayan.commons.annotation.Gender
import com.kamalnayan.commons.modifier.CharacterModifier
import com.kamalnayan.domain.domain.base.usecase.BaseUseCase
import com.kamalnayan.domain.domain.models.character.CharacterItem
import com.kamalnayan.domain.domain.repository.ICharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

/** @Author Kamal Nayan
Created on: 09/01/24
 **/
class GetCharactersUseCase @Inject constructor(private val repository: ICharactersRepository) :
    BaseUseCase<Pair<CharacterModifier, CharacterModifier>, Flow<PagingData<CharacterItem>>>() {
    override suspend fun invoke(params: Pair<CharacterModifier, CharacterModifier>?): Flow<PagingData<CharacterItem>> {
        return when (params?.second) {
            CharacterModifier.Created -> {
                repository.getCharacters()
            }

            is CharacterModifier.Default -> {
                repository.getCharacters()
            }

            is CharacterModifier.Gender -> {
                val gender = (params.second as CharacterModifier.Gender).gender ?: Gender.MALE
                return repository.getCharacters().mapLatest {
                    it.filter { it.gender == if (gender == Gender.MALE) "male" else "female" }
                }
            }

            CharacterModifier.Height -> {
                repository.getCharacters()
            }

            CharacterModifier.Name -> {
                repository.getCharacters()
            }

            CharacterModifier.Updated -> {
                repository.getCharacters()
            }

            null -> {
                repository.getCharacters()
            }
        }
    }


}