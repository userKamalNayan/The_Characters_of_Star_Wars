package com.kamalnayan.commons.annotation

import androidx.annotation.IntDef
import com.kamalnayan.commons.annotation.Gender.Companion.FEMALE
import com.kamalnayan.commons.annotation.Gender.Companion.MALE

/** @Author Kamal Nayan
Created on: 10/01/24
 **/
@IntDef(MALE, FEMALE)
annotation class Gender {
    companion object {
        const val MALE = 0
        const val FEMALE = 1
    }
}