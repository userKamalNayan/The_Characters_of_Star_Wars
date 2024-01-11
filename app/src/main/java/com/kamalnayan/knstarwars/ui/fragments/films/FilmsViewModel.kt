package com.kamalnayan.knstarwars.ui.fragments.films

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kamalnayan.domain.domain.models.film.FilmResponse
import com.kamalnayan.domain.domain.usecase.GetFilmUseCase
import com.kamalnayan.knstarwars.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmsViewModel @Inject constructor(
    private val getFilmUseCase: GetFilmUseCase
) : BaseViewModel() {

    private val filmList = mutableListOf<FilmResponse>()


    private val _films by lazy { MutableLiveData<List<FilmResponse>>() }
    val films: LiveData<List<FilmResponse>> by lazy { _films }

    fun getFilm(filmUrl: String) {
        Log.d("film", "getFilm: get film $filmUrl")

        viewModelScope.launch {
            val response = getFilmUseCase.invoke(filmUrl)
            if (response != null) {
                Log.d("film", "getFilm: GOT film ${response.url}")
                handleGetFilmSuccess(response)
            } else {
                Log.d("film", "getFilm: $filmUrl failed to get ")
            }
        }
    }

    private fun handleGetFilmSuccess(data: FilmResponse) {
        filmList.add(data)
        _films.postValue(filmList)
    }
}