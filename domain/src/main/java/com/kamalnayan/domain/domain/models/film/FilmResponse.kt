package com.kamalnayan.domain.domain.models.film

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.kamalnayan.domain.domain.base.response.BaseResponse
import kotlinx.parcelize.Parcelize

@Entity(tableName = "film_table")
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
    @PrimaryKey
    val url: String,
    val vehicles: List<String>
) : BaseResponse()