package com.kamalnayan.domain.domain.character


import com.google.gson.annotations.SerializedName
import com.kamalnayan.domain.domain.base.response.BaseResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharactersResponse(
    @SerializedName("results")
    val characterItems: List<CharacterItem>
) : BaseResponse()