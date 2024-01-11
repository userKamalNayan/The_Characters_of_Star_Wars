package com.kamalnayan.domain.domain.usecase

import com.kamalnayan.commons.annotation.Gender
import com.kamalnayan.commons.extension.Empty
import com.kamalnayan.commons.modifier.CharacterModifier
import com.kamalnayan.domain.domain.base.usecase.BaseUseCase
import com.kamalnayan.domain.domain.models.character.CharacterItem
import com.kamalnayan.domain.domain.repository.ICharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/** @Author Kamal Nayan
Created on: 09/01/24
 **/
class GetCharactersUseCase @Inject constructor(private val repository: ICharactersRepository) :
    BaseUseCase<Pair<CharacterModifier, CharacterModifier>, Flow<List<CharacterItem>>>() {

    companion object {
        private const val MALE = "male"
        private const val FEMALE = "female"
    }

    override suspend fun invoke(params: Pair<CharacterModifier, CharacterModifier>?): Flow<List<CharacterItem>> {
        val isGenderFilterEnabled = params?.second is CharacterModifier.Gender
        var genderForFilter = String.Empty
        if (isGenderFilterEnabled) {
            genderForFilter =
                if ((params?.second as CharacterModifier.Gender).gender == Gender.MALE) MALE else FEMALE
        }
        return repository.getCharacters().map { list ->
            when (params?.first) {
                CharacterModifier.Created -> {
                    list.sortedBy {
                        it.created
                    }
                }

                is CharacterModifier.Default -> {
                    list
                }

                is CharacterModifier.Gender -> {
                    list
                }

                CharacterModifier.Height -> {
                    list.sortedBy {
                        it.height
                    }
                }

                CharacterModifier.Name -> {
                    list.sortedBy {
                        it.name
                    }
                }

                CharacterModifier.Updated -> {
                    list.sortedBy {
                        it.edited
                    }
                }

                null -> {
                    list
                }
            }
        }.map { sortedList ->
            sortedList.filter {
                if (isGenderFilterEnabled) {
                    it.gender == genderForFilter
                } else true
            }
        }
    }
}