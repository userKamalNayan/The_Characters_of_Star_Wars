package com.kamalnayan.knstarwars.epoxy.controller

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.kamalnayan.domain.domain.models.character.CharacterItem
import com.kamalnayan.knstarwars.epoxy.models.CharacterModel_
import com.kamalnayan.knstarwars.util.`typealias`.CharacterClickCallback

/** @Author Kamal Nayan
Created on: 10/01/24
 **/
class CharactersController : PagingDataEpoxyController<CharacterItem>() {
    var onCharacterItemClick: CharacterClickCallback = null
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildItemModel(currentPosition: Int, item: CharacterItem?): EpoxyModel<*> {
        return CharacterModel_().id(item?.id.toString().plus(item?.name)).apply {
            characterItem(item)
            characterClickCallback {
                item?.let { this@CharactersController.onCharacterItemClick?.invoke(it) }
            }
        }
    }
}

