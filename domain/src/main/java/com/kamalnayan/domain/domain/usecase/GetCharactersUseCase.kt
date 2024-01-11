package com.kamalnayan.domain.domain.usecase

import com.kamalnayan.commons.annotation.Gender
import com.kamalnayan.commons.extension.Empty
import com.kamalnayan.commons.modifier.CharacterModifier
import com.kamalnayan.domain.domain.base.usecase.BaseUseCase
import com.kamalnayan.domain.domain.models.character.CharacterItem
import com.kamalnayan.domain.domain.repository.ICharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/** @Author Kamal Nayan
 *Created on: 09/01/24
 *
 * Fetches characters data from local db, then performs modifications
 * like sorting/ filtering as required.
 **/
class GetCharactersUseCase @Inject constructor(private val repository: ICharactersRepository) :
    BaseUseCase<Pair<CharacterModifier, CharacterModifier>, Flow<List<CharacterItem>>>() {

    companion object {
        private const val MALE = "male"
        private const val FEMALE = "female"
    }

    override suspend fun invoke(params: Pair<CharacterModifier, CharacterModifier>?): Flow<List<CharacterItem>> {
        return withContext(Dispatchers.IO) {
            val isGenderFilterEnabled = params?.second is CharacterModifier.Gender
            var genderToFilter = String.Empty
            if (isGenderFilterEnabled) {
                genderToFilter =
                    if ((params?.second as CharacterModifier.Gender).gender == Gender.MALE) MALE else FEMALE
            }

            repository.getCharacters().map { list ->
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
            }.apply {
                /*
                Perform gender filtration if any applied
                 */
                if (isGenderFilterEnabled) {
                    return@withContext map { sortedList ->
                        sortedList.filter {
                            it.gender == genderToFilter
                        }
                    }
                }
            }
        }
    }
}