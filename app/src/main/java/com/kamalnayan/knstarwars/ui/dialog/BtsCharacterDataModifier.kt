package com.kamalnayan.knstarwars.ui.dialog

import com.kamalnayan.commons.annotation.Gender
import com.kamalnayan.knstarwars.R
import com.kamalnayan.knstarwars.base.BaseBottomSheetFragment
import com.kamalnayan.knstarwars.databinding.BtsCharacterDataModifierBinding
import com.kamalnayan.commons.modifier.CharacterModifier
import com.kamalnayan.commons.modifier.ModifierType
import com.kamalnayan.knstarwars.util.`typealias`.CharacterModifierChangeCallback

class BtsCharacterDataModifier :
    BaseBottomSheetFragment<BtsCharacterDataModifierBinding>(BtsCharacterDataModifierBinding::inflate) {


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment BtsCharacterDataModifier.
         */
        @JvmStatic
        fun newInstance() = BtsCharacterDataModifier()
    }

    /**
     * Callback for selection change.
     * invoked when value is changed of [sortAndFilterSelection]
     * @see [sortAndFilterSelection]
     */
    private var selectionChangedListener: CharacterModifierChangeCallback? = null

    private var sortAndFilterSelection: Pair<CharacterModifier, CharacterModifier> =
        CharacterModifier.Default(ModifierType.Sort) to CharacterModifier.Default(ModifierType.Filter)
        set(value) {
            field = value
            selectionChangedListener?.invoke(value)
        }

    fun setSelectedSortAndFilter(sortAndFilterSelection: Pair<CharacterModifier, CharacterModifier>) {
        this.sortAndFilterSelection = sortAndFilterSelection
    }

    fun setSelectionChangedListener(selectionChangedListener: CharacterModifierChangeCallback) {
        this.selectionChangedListener = selectionChangedListener
    }

    override fun fetchData() {

    }

    override fun setViewModelToBinding() {

    }

    override fun initViews() {
        setupSelectedOption()
    }

    private fun setupSelectedOption() {
        when (sortAndFilterSelection.first) {
            CharacterModifier.Created -> {
                binding.radioGroup.check(R.id.radio_created)
            }

            is CharacterModifier.Default -> {
                binding.radioGroup.check(R.id.radio_default)
            }

            is CharacterModifier.Gender -> {
                // do nothing
            }

            CharacterModifier.Height -> {
                binding.radioGroup.check(R.id.radio_height)
            }

            CharacterModifier.Name -> {
                binding.radioGroup.check(R.id.radio_name)
            }

            CharacterModifier.Updated -> {
                binding.radioGroup.check(R.id.radio_updated)
            }
        }

        when (sortAndFilterSelection.second) {
            CharacterModifier.Created -> {
                // do nothing
            }

            is CharacterModifier.Default -> {
                binding.filterRadioGroup.check(R.id.radio_none)
            }

            is CharacterModifier.Gender -> {
                val radioId = getRadioIdForGender()
                if (radioId != -1)
                    binding.filterRadioGroup.check(radioId)
            }

            CharacterModifier.Height -> {
                // do nothing
            }

            CharacterModifier.Name -> {
                // do nothing
            }

            CharacterModifier.Updated -> {
                // do nothing
            }
        }
    }

    private fun getRadioIdForGender(): Int {
        return when ((sortAndFilterSelection.second as CharacterModifier.Gender).gender) {
            Gender.MALE -> return R.id.radio_male
            Gender.FEMALE -> return R.id.radio_female
            else -> {
                -1
            }
        }
    }

    override fun setData() {

    }

    override fun setListeners() {
        with(binding) {
            radioGroup.setOnCheckedChangeListener { radioGroup, i ->
                handleSortRadioClick(i)
            }

            filterRadioGroup.setOnCheckedChangeListener { radioGroup, id ->
                handleFilterRadioClick(id)
            }
        }
    }

    private fun handleFilterRadioClick(id: Int) {
        when (id) {
            R.id.radio_none -> {
                sortAndFilterSelection =
                    sortAndFilterSelection.first to CharacterModifier.Default(ModifierType.Filter)
            }

            R.id.radio_female -> {
                sortAndFilterSelection = sortAndFilterSelection.first to CharacterModifier.Gender(
                    Gender.FEMALE
                )
            }

            R.id.radio_male -> {
                sortAndFilterSelection = sortAndFilterSelection.first to CharacterModifier.Gender(
                    Gender.MALE
                )
            }
        }
    }

    private fun handleSortRadioClick(id: Int) {
        when (id) {
            R.id.radio_default -> {
                sortAndFilterSelection =
                    CharacterModifier.Default(ModifierType.Sort) to sortAndFilterSelection.second
            }

            R.id.radio_name -> {
                sortAndFilterSelection = CharacterModifier.Name to sortAndFilterSelection.second
            }

            R.id.radio_height -> {
                sortAndFilterSelection = CharacterModifier.Height to sortAndFilterSelection.second
            }

            R.id.radio_created -> {
                sortAndFilterSelection = CharacterModifier.Created to sortAndFilterSelection.second
            }

            R.id.radio_updated -> {
                sortAndFilterSelection = CharacterModifier.Updated to sortAndFilterSelection.second
            }
        }

    }

    override fun setObservers() {

    }
}