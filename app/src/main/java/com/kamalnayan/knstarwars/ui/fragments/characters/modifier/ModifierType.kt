package com.kamalnayan.knstarwars.ui.fragments.characters.modifier

/** @Author Kamal Nayan
Created on: 10/01/24
 **/
sealed class ModifierType {
 data object Sort:ModifierType()
 data object Filter:ModifierType()
}