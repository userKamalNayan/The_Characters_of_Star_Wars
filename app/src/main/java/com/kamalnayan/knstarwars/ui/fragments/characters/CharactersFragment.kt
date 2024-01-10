package com.kamalnayan.knstarwars.ui.fragments.characters

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kamalnayan.knstarwars.base.BaseFragment
import com.kamalnayan.knstarwars.databinding.FragmentCharactersBinding
import com.kamalnayan.knstarwars.epoxy.controller.CharactersController
import com.kamalnayan.knstarwars.util.extension.loadMoreListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment :
    BaseFragment<FragmentCharactersBinding>(FragmentCharactersBinding::inflate) {

    private val viewModel by viewModels<CharacterViewModel>()
    private val controller by lazy {
        CharactersController()
    }

    override fun fetchData() {
        if (controller.charactersItems.isNullOrEmpty())
            viewModel.getCharacters()
    }

    override fun setViewModelToBinding() {
        binding.loader.vm = viewModel
    }

    override fun initViews() {
        with(binding) {
            epoxyRecycler.setController(controller)
        }
    }

    override fun setData() {
    }

    override fun setListeners() {
        with(binding) {
            epoxyRecycler.loadMoreListener {

            }
        }

        controller.onCharacterItemClick = {
            findNavController().navigate(
                CharactersFragmentDirections.actionFragmentCharactersToFragmentFilms(
                    it
                )
            )
        }
    }

    override fun setObservers() {
        with(viewModel) {
            charactersData.observe(viewLifecycleOwner) { response ->
                response?.let {
                    controller.charactersItems = it
                }
            }
        }
    }

}