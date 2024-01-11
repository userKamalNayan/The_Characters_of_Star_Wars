package com.kamalnayan.knstarwars.ui.fragments.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kamalnayan.commons.modifier.CharacterModifier
import com.kamalnayan.domain.domain.models.character.CharacterItem
import com.kamalnayan.domain.domain.usecase.FetchCharactersFromRemoteUseCase
import com.kamalnayan.domain.domain.usecase.GetCharactersUseCase
import com.kamalnayan.knstarwars.base.BaseViewModel
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onSuccess
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/** @Author Kamal Nayan
Created on: 09/01/24
 **/
@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val fetchCharactersFromRemoteUseCase: Lazy<FetchCharactersFromRemoteUseCase>
) : BaseViewModel() {

    companion object {
        private const val PAGE_SIZE = 10
    }

    /**
     * Used to prevent duplicate api calls during paging
     */
    private var isLoading = false

    /**
     * Used to check whether more data is available or not
     */
    private var canLoadMore = true

    private var getCharactersJob: Job? = null

    private val _characters by lazy { MutableLiveData<List<CharacterItem>>() }
    val characters: LiveData<List<CharacterItem>> by lazy { _characters }

    private val _isNextPageLoading by lazy { MutableLiveData<Boolean>() }
    val isNextPageLoading: LiveData<Boolean> by lazy { _isNextPageLoading }


    /**
     * Fetches characters data from local db
     * if data is null then we fetch data from remote
     *
     * @see fetchCharactersDataFromRemote
     */
    fun getCharactersData(sortAndFilterSelection: Pair<CharacterModifier, CharacterModifier>) {
        getCharactersJob?.cancel()
        getCharactersJob = viewModelScope.launch {
            getCharactersUseCase(sortAndFilterSelection).collect {
                _loading.postValue(it.isNullOrEmpty())
                if (it.isNullOrEmpty()) {
                    // load first page , as the db is empty
                    fetchCharactersDataFromRemote(1)
                }
                _characters.postValue(it)
            }
        }
    }


    /**
     * Fetches data from remote. Data is stored in local DB
     *
     * @see com.kamalnayan.data.repository.CharacterRepository.fetchCharactersFromRemote
     */
    fun fetchCharactersDataFromRemote(size: Int) {
        val pageNumber = (size / PAGE_SIZE) + 1
        if (isLoading || !canLoadMore)
            return
        isLoading = true
        viewModelScope.launch {
            if (pageNumber > 1)
                _isNextPageLoading.postValue(true)
            val response = fetchCharactersFromRemoteUseCase.get().invoke(pageNumber)
            response.onSuccess {
                isLoading = false
                canLoadMore = !this.data.next.isNullOrBlank()
                _isNextPageLoading.postValue(false)
            }.onError {
                isLoading = false
                canLoadMore = false
                _isNextPageLoading.postValue(false)
            }
        }
    }

}