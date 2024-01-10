package com.kamalnayan.knstarwars.ui.fragments.characters.modifier

/** @Author Kamal Nayan
Created on: 10/01/24
 **/
sealed class CharacterModifier(private val modifierType: ModifierType) {
    class Default(modifierType: ModifierType) : CharacterModifier(modifierType)
    class Name(modifierType: ModifierType) : CharacterModifier(modifierType)
    class Gender(modifierType: ModifierType) : CharacterModifier(modifierType)
    class Created(modifierType: ModifierType) : CharacterModifier(modifierType)
    class Updated(modifierType: ModifierType) : CharacterModifier(modifierType)
}