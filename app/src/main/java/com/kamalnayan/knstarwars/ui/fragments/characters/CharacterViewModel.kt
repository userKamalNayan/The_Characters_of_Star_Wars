package com.kamalnayan.knstarwars.ui.fragments.characters

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kamalnayan.domain.domain.usecase.GetCharactersUseCase
import com.kamalnayan.knstarwars.base.BaseViewModel
import com.kamalnayan.commons.modifier.CharacterModifier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/** @Author Kamal Nayan
Created on: 09/01/24
 **/
@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : BaseViewModel() {

    suspend fun charactersData(sortAndFilterSelection: Pair<CharacterModifier, CharacterModifier>) = getCharactersUseCase(sortAndFilterSelection).cachedIn(
        viewModelScope
    )

}