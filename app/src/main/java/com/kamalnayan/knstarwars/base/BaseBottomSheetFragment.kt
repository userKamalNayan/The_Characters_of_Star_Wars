package com.kamalnayan.knstarwars.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kamalnayan.knstarwars.R
import com.kamalnayan.knstarwars.util.autoCleared

/** @Author Kamal Nayan
Created on: 09/01/24
 **/
abstract class BaseBottomSheetFragment<B : ViewDataBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> B
) : BottomSheetDialogFragment() {

    protected var binding by autoCleared<B>()

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = bindingInflater.invoke(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchData()
        initViews()
        setViewModelToBinding()
        setData()
        setListeners()
        setObservers()
    }

    /**
     * Used to set view model to view binding
     */
    abstract fun fetchData()
    abstract fun setViewModelToBinding()

    abstract fun initViews()

    abstract fun setData()

    abstract fun setListeners()

    abstract fun setObservers()

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetStyle
    }

    protected fun Int.toStringFromResourceId(): String =
        context?.resources?.getString(this) ?: ""

    protected fun Int.toColor() = ContextCompat.getColor(requireContext(), this)
}