package com.kamalnayan.commons.modifier

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/** @Author Kamal Nayan
Created on: 10/01/24
 **/
@Parcelize
sealed class ModifierType : Parcelable {
    data object Sort : ModifierType()
    data object Filter : ModifierType()
}