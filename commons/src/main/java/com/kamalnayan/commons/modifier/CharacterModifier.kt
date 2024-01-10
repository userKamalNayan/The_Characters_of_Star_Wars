package com.kamalnayan.commons.modifier

/** @Author Kamal Nayan
Created on: 10/01/24
 **/
sealed class CharacterModifier(private val modifierType: ModifierType) {
    class Default(modifierType: ModifierType) : CharacterModifier(modifierType)
    data object Name : CharacterModifier(ModifierType.Sort)
    data class Gender(
        @com.kamalnayan.commons.annotation.Gender val gender: Int
    ) : CharacterModifier(ModifierType.Filter)

    data object Created : CharacterModifier(ModifierType.Sort)
    data object Updated : CharacterModifier(ModifierType.Sort)
    data object Height : CharacterModifier(ModifierType.Sort)
}