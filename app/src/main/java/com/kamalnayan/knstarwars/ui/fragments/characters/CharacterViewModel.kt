package com.kamalnayan.knstarwars.ui.fragments.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kamalnayan.commons.modifier.CharacterModifier
import com.kamalnayan.data.error.ErrorEnvelopeMapper
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

    private val _error by lazy { MutableLiveData<String>() }
    val error: LiveData<String> by lazy { _error }


    /**
     * Holds the page number which will be fetched from remote
     *
     * @see fetchCharactersDataFromRemote
     */
    private var pageNumberToLoad: Int = Integer.MIN_VALUE


    /**
     * Fetches characters data from local db
     * if data is null then we fetch data from remote using [fetchCharactersDataFromRemote]
     *
     * When data is received we update the [pageNumberToLoad] value.
     */
    fun getCharactersData(sortAndFilterSelection: Pair<CharacterModifier, CharacterModifier>) {
        getCharactersJob?.cancel()
        getCharactersJob = viewModelScope.launch {
            getCharactersUseCase(sortAndFilterSelection).collect {
                if (it.isNullOrEmpty()) {
                    // load first page , as the db is empty
                    _loading.postValue(true)
                    pageNumberToLoad = 1
                    fetchCharactersDataFromRemote()
                } else {
                    pageNumberToLoad = (it.size / PAGE_SIZE) + 1
                    // for case where 82 items are available,
                    // it means page 9 has 2 items only , hence this is last page
                    canLoadMore = it.size % PAGE_SIZE == 0
                }

                _characters.postValue(it)
            }
        }
    }


    /**
     * Fetches data from remote.
     *
     * assigning [Integer.MIN_VALUE] to [pageNumberToLoad] in order to
     * prevent duplicate page calls. when new page data is received
     * [getCharactersData] gets the updated data and there [pageNumberToLoad]
     * is updated. so in this way duplication is prevented
     *
     * @see com.kamalnayan.data.repository.CharacterRepository.fetchCharactersFromRemote
     */
    fun fetchCharactersDataFromRemote() {
        if (isLoading || !canLoadMore || pageNumberToLoad == Integer.MIN_VALUE)
            return

        isLoading = true
        viewModelScope.launch {
            if (pageNumberToLoad > 1)
                _isNextPageLoading.postValue(true)
            val response = fetchCharactersFromRemoteUseCase.get().invoke(pageNumberToLoad)
            response.onSuccess {
                pageNumberToLoad = Integer.MIN_VALUE
                canLoadMore = !this.data.next.isNullOrBlank()
                _isNextPageLoading.postValue(false)
                isLoading = false
                _loading.postValue(false)
            }.onError(ErrorEnvelopeMapper) {
                isLoading = false
                canLoadMore = false
                _error.postValue(this.apiErrorMessage.toString())
                _isNextPageLoading.postValue(false)
                _loading.postValue(false)
            }
        }
    }

}