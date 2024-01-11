package com.kamalnayan.knstarwars.ui.fragments.characters

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kamalnayan.commons.modifier.CharacterModifier
import com.kamalnayan.commons.modifier.ModifierType
import com.kamalnayan.knstarwars.R
import com.kamalnayan.knstarwars.base.BaseFragment
import com.kamalnayan.knstarwars.databinding.FragmentCharactersBinding
import com.kamalnayan.knstarwars.epoxy.controller.CharactersController
import com.kamalnayan.knstarwars.ui.dialog.BtsCharacterDataModifier
import com.kamalnayan.knstarwars.util.extension.loadMoreListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment :
    BaseFragment<FragmentCharactersBinding>(FragmentCharactersBinding::inflate) {

    companion object {
        private val DEFAULT_SORT_FILTER =
            CharacterModifier.Default(ModifierType.Sort) to CharacterModifier.Default(ModifierType.Filter)
    }

    private val viewModel by viewModels<CharacterViewModel>()
    private val controller by lazy {
        CharactersController()
    }

    private var sortAndFilterSelection: Pair<CharacterModifier, CharacterModifier> =
        DEFAULT_SORT_FILTER
        set(value) {
            field = value
            fetchData()
            setIndicatorVisibility()
        }

    /**
     * Handles visibility of filter indicator.
     * Shows when any modifier is applied else hides
     */
    private fun setIndicatorVisibility() {
        binding.toolbar.ivFilterActive.isVisible =
            sortAndFilterSelection.first  !is  CharacterModifier.Default || sortAndFilterSelection.second !is CharacterModifier.Default
    }

    override fun fetchData() {
        viewModel.getCharactersData(sortAndFilterSelection)
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
            setIndicatorVisibility()
        }
    }

    override fun setData() {
    }

    override fun setListeners() {
        with(binding) {
            toolbar.ivMore.setOnClickListener {
                showModifierBottomSheet()
            }

            epoxyRecycler.loadMoreListener {
                viewModel.fetchCharactersDataFromRemote(controller.characterList?.size ?: 1)
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
        val bottomSheet = BtsCharacterDataModifier()
        bottomSheet.apply {
            setSelectedSortAndFilter(this@CharactersFragment.sortAndFilterSelection)
            setSelectionChangedListener {
                this@CharactersFragment.sortAndFilterSelection = it
            }
        }
        bottomSheet.show(childFragmentManager, null)
    }

    override fun setObservers() {
        with(viewModel) {
            characters.observe(viewLifecycleOwner) { response ->
                response?.let {
                    controller.characterList = it
                }
            }
            isNextPageLoading.observe(viewLifecycleOwner) { response ->
                response?.let {
                    controller.isLoading = it
                }
            }
        }
    }

}