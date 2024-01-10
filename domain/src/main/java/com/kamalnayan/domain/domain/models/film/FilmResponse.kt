package com.kamalnayan.domain.domain.models.film

import com.google.gson.annotations.SerializedName
import com.kamalnayan.domain.domain.base.response.BaseResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilmResponse(
    val characters: List<String>,
    val created: String,
    val director: String,
    val edited: String,
    @SerializedName("episode_id")
    val episodeId: Int,
    @SerializedName("opening_crawl")
    val openingCrawl: String,
    val planets: List<String>,
    val producer: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val species: List<String>,
    val starships: List<String>,
    val title: String,
    val url: String,
    val vehicles: List<String>
) : BaseResponse()