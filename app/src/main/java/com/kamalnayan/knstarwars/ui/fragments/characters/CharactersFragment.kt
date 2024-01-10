package com.kamalnayan.knstarwars.ui.fragments.characters

import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kamalnayan.commons.modifier.CharacterModifier
import com.kamalnayan.commons.modifier.ModifierType
import com.kamalnayan.knstarwars.R
import com.kamalnayan.knstarwars.base.BaseFragment
import com.kamalnayan.knstarwars.databinding.FragmentCharactersBinding
import com.kamalnayan.knstarwars.epoxy.controller.CharactersController
import com.kamalnayan.knstarwars.ui.dialog.BtsCharacterDataModifier
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersFragment :
    BaseFragment<FragmentCharactersBinding>(FragmentCharactersBinding::inflate) {

    private val viewModel by viewModels<CharacterViewModel>()
    private val controller by lazy {
        CharactersController()
    }

    private var sortAndFilterSelection: Pair<CharacterModifier, CharacterModifier> =
        CharacterModifier.Default(ModifierType.Sort) to CharacterModifier.Default(ModifierType.Filter)
        set(value) {
            field = value
            setObservers()
        }

    override fun fetchData() {
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
            lifecycleScope.launch {
                charactersData(sortAndFilterSelection)
                    .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                    .collectLatest {
                        controller.submitData(it)
                    }
            }
        }
    }

}