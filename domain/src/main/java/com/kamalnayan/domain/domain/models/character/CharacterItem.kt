package com.kamalnayan.domain.domain.models.character


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.kamalnayan.domain.domain.base.response.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "character_table")
data class CharacterItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("birth_year")
    val birthYear: String,
    val created: String,
    val edited: String,
    @SerializedName("eye_color")
    val eyeColor: String,
    val films: List<String>,
    val gender: String,
    @SerializedName("hair_color")
    val hairColor: String,
    val height: String,
    @SerializedName("homeworld")
    val homeWorld: String,
    val mass: String,
    val name: String,
    @SerializedName("skin_color")
    val skinColor: String,
    val species: List<String>,
    val starships: List<String>,
    val url: String,
    val vehicles: List<String>
) : BaseModel()