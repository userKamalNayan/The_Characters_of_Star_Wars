package com.kamalnayan.knstarwars.epoxy.controller

import com.airbnb.epoxy.AsyncEpoxyController
import com.kamalnayan.domain.domain.models.character.CharacterItem
import com.kamalnayan.knstarwars.epoxy.models.character
import com.kamalnayan.knstarwars.loader
import com.kamalnayan.knstarwars.util.`typealias`.CharacterClickCallback

/** @Author Kamal Nayan
Created on: 10/01/24
 **/
class CharactersController : AsyncEpoxyController() {
    var onCharacterItemClick: CharacterClickCallback = null
        set(value) {
            field = value
            requestModelBuild()
        }

    var characterList: List<CharacterItem>? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    var isLoading: Boolean = false
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        buildCharactersUi()
        if (isLoading)
            buildLoader()
    }

    private fun buildLoader() {
        loader {
            id("loader")
        }
    }

    private fun buildCharactersUi() {
        characterList?.forEachIndexed { index, item ->
            character {
                id(item.id)
                characterItem(item)
                characterClickCallback { clickedItem ->
                    clickedItem?.let { this@CharactersController.onCharacterItemClick?.invoke(it) }
                }
            }
        }
    }
}

