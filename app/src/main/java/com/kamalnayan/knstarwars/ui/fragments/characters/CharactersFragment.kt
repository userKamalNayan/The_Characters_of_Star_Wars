package com.kamalnayan.knstarwars.ui.fragments.characters

import androidx.fragment.app.viewModels
import com.kamalnayan.knstarwars.base.BaseFragment
import com.kamalnayan.knstarwars.databinding.FragmentCharactersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment :
    BaseFragment<FragmentCharactersBinding>(FragmentCharactersBinding::inflate) {

    private val viewModel by viewModels<CharacterViewModel>()

    override fun fetchData() {
        viewModel.getCharacters()
    }

    override fun setViewModelToBinding() {
        binding.loader.vm = viewModel
    }

    override fun initViews() {
    }

    override fun setData() {
    }

    override fun setListeners() {
    }

    override fun setObservers() {
        with(viewModel) {
            charactersData.observe(viewLifecycleOwner) { response ->
                response?.let { }
            }
        }
    }

}