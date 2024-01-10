package com.kamalnayan.knstarwars.ui.fragments.films

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.kamalnayan.knstarwars.base.BaseFragment
import com.kamalnayan.knstarwars.databinding.FragmentFilmsBinding

class FilmsFragment : BaseFragment<FragmentFilmsBinding>(FragmentFilmsBinding::inflate) {

    companion object {
        fun newInstance() = FilmsFragment()
    }

    private val args by navArgs<FilmsFragmentArgs>()

    private val viewModel by viewModels<FilmsViewModel>()

    override fun fetchData() {

    }

    override fun setViewModelToBinding() {

    }

    override fun initViews() {
        Toast.makeText(requireContext(), args.character?.name, Toast.LENGTH_SHORT).show()
    }

    override fun setData() {

    }

    override fun setListeners() {

    }

    override fun setObservers() {

    }


}