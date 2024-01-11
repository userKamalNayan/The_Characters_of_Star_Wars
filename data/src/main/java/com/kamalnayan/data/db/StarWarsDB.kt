package com.kamalnayan.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kamalnayan.data.db.dao.CharactersDao
import com.kamalnayan.domain.domain.models.character.CharacterItem
import com.kamalnayan.domain.domain.models.film.FilmResponse

/** @Author Kamal Nayan
Created on: 10/01/24
 **/

@Database(entities = [CharacterItem::class, FilmResponse::class], version = 1, exportSchema = false)
@TypeConverters(com.kamalnayan.data.db.converter.TypeConverters::class)
abstract class StarWarsDB : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao
}