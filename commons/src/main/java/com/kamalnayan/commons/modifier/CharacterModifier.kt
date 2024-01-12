package com.kamalnayan.commons.modifier

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize

/** @Author Kamal Nayan
Created on: 10/01/24
 **/

/**
 * Used for performing Sorting and Filtration operation on characters data
 */
@Parcelize
sealed class CharacterModifier(private val modifierType: ModifierType):Parcelable {
    class Default(private val modifierType: ModifierType) : CharacterModifier(modifierType)
    data object Name : CharacterModifier(ModifierType.Sort)
    data class Gender(
        @com.kamalnayan.commons.annotation.Gender val gender: Int
    ) : CharacterModifier(ModifierType.Filter)

    data object Created : CharacterModifier(ModifierType.Sort)
    data object Updated : CharacterModifier(ModifierType.Sort)
    data object Height : CharacterModifier(ModifierType.Sort)
}