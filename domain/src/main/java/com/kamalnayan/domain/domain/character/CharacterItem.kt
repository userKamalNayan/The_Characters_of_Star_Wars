package com.kamalnayan.domain.domain.character


import com.google.gson.annotations.SerializedName
import com.kamalnayan.domain.domain.base.response.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterItem(
    val birthYear: String,
    val created: String,
    val edited: String,
    val eyeColor: String,
    val films: List<String>,
    val gender: String,
    val hairColor: String,
    val height: String,
    @SerializedName("homeworld")
    val homeWorld: String,
    val mass: String,
    val name: String,
    val skinColor: String,
    val species: List<String>,
    val starships: List<String>,
    val url: String,
    val vehicles: List<String>
) : BaseModel()