package com.kamalnayan.knstarwars.ui.fragments.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kamalnayan.domain.domain.models.character.CharacterItem
import com.kamalnayan.domain.domain.usecase.GetCharactersUseCase
import com.kamalnayan.knstarwars.base.BaseViewModel
import com.skydoves.sandwich.onSuccess
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

    private val _charactersData by lazy { MutableLiveData<List<CharacterItem>>() }
    val charactersData: LiveData<List<CharacterItem>> by lazy { _charactersData }

    init {
        getCharacters()
    }

    /**
     * Fetches all the characters of star wars movie
     */
    fun getCharacters() {
        viewModelScope.launch {
            _loading.postValue(true)
            val response = getCharactersUseCase()
            response.onSuccess {
                _charactersData.postValue(this.data.characterItems)
                _loading.postValue(false)
            }
        }
    }


}