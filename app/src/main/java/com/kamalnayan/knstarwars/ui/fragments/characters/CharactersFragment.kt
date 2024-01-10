package com.kamalnayan.knstarwars.ui.fragments.characters

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kamalnayan.knstarwars.R
import com.kamalnayan.knstarwars.base.BaseFragment
import com.kamalnayan.knstarwars.databinding.FragmentCharactersBinding
import com.kamalnayan.knstarwars.epoxy.controller.CharactersController
import com.kamalnayan.knstarwars.ui.dialog.BtsCharacterDataModifier
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment :
    BaseFragment<FragmentCharactersBinding>(FragmentCharactersBinding::inflate) {

    private val viewModel by viewModels<CharacterViewModel>()
    private val controller by lazy {
        CharactersController()
    }

    //Give an option for filtering and sorting based on name, gender, created, updated, etc.
// Filter and sort option item list should open in a bottom sheet.

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
            with(toolbar) {
                tvTitle.text = getString(R.string.title_characters_screen)
            }
        }
    }

    override fun setData() {
    }

    override fun setListeners() {
        with(binding) {
            toolbar.ivMore.setOnClickListener {
                showModifierBottomSheet()
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

    private fun showModifierBottomSheet() {
        BtsCharacterDataModifier().show(parentFragmentManager, null)
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