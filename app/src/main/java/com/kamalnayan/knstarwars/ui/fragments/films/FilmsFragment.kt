package com.kamalnayan.knstarwars.ui.fragments.films

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.kamalnayan.domain.domain.models.film.FilmResponse
import com.kamalnayan.knstarwars.R
import com.kamalnayan.knstarwars.base.BaseFragment
import com.kamalnayan.knstarwars.databinding.FragmentFilmsBinding
import com.kamalnayan.knstarwars.film
import com.kamalnayan.knstarwars.filmSkeleton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilmsFragment : BaseFragment<FragmentFilmsBinding>(FragmentFilmsBinding::inflate) {

    companion object {
        fun newInstance() = FilmsFragment()
    }

    private val args by navArgs<FilmsFragmentArgs>()

    private val viewModel by viewModels<FilmsViewModel>()

    private var filmsList = listOf<FilmResponse>()
        set(value) {
            field = value
            binding.epoxyRecycler.requestModelBuild()
        }

    override fun fetchData() {
        if (viewModel.films.value.isNullOrEmpty()) {
            args.character?.films?.forEach {
                viewModel.getFilm(it)
            }
        }
    }

    override fun setViewModelToBinding() {

    }

    override fun initViews() {
        setToolbar()
        buildFilmsUi()
    }

    private fun setToolbar() {
        with(binding.toolbar){
            ivMore.isVisible=false
            tvTitle.text = getString(R.string.format_film_by,args.character?.name.orEmpty())
        }
    }

    override fun setData() {

    }

    override fun setListeners() {

    }

    override fun setObservers() {
        with(viewModel) {
            films.observe(viewLifecycleOwner) { response ->
                response?.let {
                    filmsList = it
                }
            }
        }
    }

    private fun buildFilmsUi() {
        binding.epoxyRecycler.withModels {
            filmsList.forEachIndexed { index, filmResponse ->
                film {
                    id(filmResponse.title)
                    name(filmResponse.title)
                    openingCrawl(filmResponse.openingCrawl)
                    releasedOn(filmResponse.releaseDate)
                }
            }

            // build shimmer for the items which are being loaded
           ( 0 until ((args.character?.films?.size ?: 0).minus(filmsList.size))).forEach {
                filmSkeleton {
                    id(it)
                }
           }
        }
    }
}