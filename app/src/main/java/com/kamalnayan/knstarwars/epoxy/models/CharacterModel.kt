package com.kamalnayan.knstarwars.epoxy.models

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kamalnayan.domain.domain.character.CharacterItem
import com.kamalnayan.knstarwars.R
import com.kamalnayan.knstarwars.databinding.ItemEpoxyCharacterItemBinding
import com.kamalnayan.knstarwars.util.`typealias`.CharacterClickCallback

/** @Author Kamal Nayan
Created on: 10/01/24
 **/
@EpoxyModelClass()
abstract class CharacterModel : EpoxyModelWithHolder<CharacterModel.Holder>() {

    @EpoxyAttribute
    var characterItem: CharacterItem? = null

    @EpoxyAttribute
    var characterClickCallback: CharacterClickCallback = null

    inner class Holder : EpoxyHolder() {
        lateinit var binding: ItemEpoxyCharacterItemBinding
        override fun bindView(itemView: View) {
            binding = ItemEpoxyCharacterItemBinding.bind(itemView)
        }
    }

    override fun getDefaultLayout(): Int {
        return R.layout.item_epoxy_character_item
    }

    override fun bind(holder: Holder) {
        super.bind(holder)
        with(holder.binding) {
            characterItem?.let { item ->
                tvCharacter.text = item.name.orEmpty()
                tvGenderValue.text = item.gender
                tvHeightValue.text = item.height

                parentContainer.setOnClickListener {
                    characterClickCallback?.invoke(item)
                }
            }
        }
    }

}