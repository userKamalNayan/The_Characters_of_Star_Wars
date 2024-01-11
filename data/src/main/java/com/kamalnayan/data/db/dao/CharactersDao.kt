package com.kamalnayan.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kamalnayan.domain.domain.models.character.CharacterItem
import kotlinx.coroutines.flow.Flow

/** @Author Kamal Nayan
Created on: 10/01/24
 **/
@Dao
interface CharactersDao {

    @Upsert
    fun insertCharacters(facilityDbEntity: List<CharacterItem>)

    @Query("SELECT * FROM character_table")
    fun getCharacters(): Flow<List<CharacterItem>>

}