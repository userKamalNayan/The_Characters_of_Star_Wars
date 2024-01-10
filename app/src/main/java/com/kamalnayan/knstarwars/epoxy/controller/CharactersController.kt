package com.kamalnayan.knstarwars.epoxy.controller

import com.airbnb.epoxy.AsyncEpoxyController
import com.kamalnayan.domain.domain.character.CharacterItem
import com.kamalnayan.knstarwars.epoxy.models.character
import com.kamalnayan.knstarwars.util.`typealias`.CharacterClickCallback

/** @Author Kamal Nayan
Created on: 10/01/24
 **/
class CharactersController : AsyncEpoxyController() {

    var charactersItems: List<CharacterItem>? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    var onCharacterItemClick: CharacterClickCallback = null
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        charactersItems?.let {
            buildCharacterModels()
        }
    }

    private fun buildCharacterModels() {
        charactersItems?.forEach { item ->
            character {
                id(item.name)
                characterItem(item)
                characterClickCallback {
                    this@CharactersController.onCharacterItemClick?.invoke(it)
                }
            }
        }
    }
}