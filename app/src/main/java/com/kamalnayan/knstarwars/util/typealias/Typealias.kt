package com.kamalnayan.knstarwars.util.`typealias`

import com.kamalnayan.domain.domain.models.character.CharacterItem
import com.kamalnayan.commons.modifier.CharacterModifier

/** @Author Kamal Nayan
Created on: 10/01/24
 **/

typealias CharacterClickCallback = ((CharacterItem) -> Unit)?
typealias CharacterModifierChangeCallback = ((Pair<CharacterModifier, CharacterModifier>) -> Unit)