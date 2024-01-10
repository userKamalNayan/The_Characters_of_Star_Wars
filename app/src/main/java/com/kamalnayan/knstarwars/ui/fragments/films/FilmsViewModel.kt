package com.kamalnayan.knstarwars.ui.fragments.films

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kamalnayan.domain.domain.models.film.FilmResponse
import com.kamalnayan.domain.domain.usecase.GetFilmUseCase
import com.kamalnayan.knstarwars.base.BaseViewModel
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmsViewModel @Inject constructor(
    private val getFilmUseCase: GetFilmUseCase
) : BaseViewModel() {

    private val _films by lazy { MutableLiveData<List<FilmResponse>>() }
    val films: LiveData<List<FilmResponse>> by lazy { _films }

    fun getFilm(filmUrl: String) {
        viewModelScope.launch {
            val response = getFilmUseCase.invoke(filmUrl)
            response.onSuccess {
                handleGetFilmSuccess(this.data)
            }
        }
    }

    private fun handleGetFilmSuccess(data: FilmResponse) {
        if (_films.value.isNullOrEmpty()) {
            _films.postValue(listOf(data))
        } else {
            val finalList = (_films.value ?: emptyList<FilmResponse>()) + listOf(data)
            _films.postValue(finalList)
        }
    }
}