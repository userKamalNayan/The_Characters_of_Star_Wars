package com.kamalnayan.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder

/** @Author Kamal Nayan
Created on: 10/01/24
 **/
class TypeConverters {

    @TypeConverter
    fun fromListStringToString(value: List<String>): String {
        val gson = GsonBuilder().create()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toListStringFromString(value: String): List<String> {
        if (value.isNullOrBlank())
            return emptyList()

        val gson = GsonBuilder().create()
        return gson.fromJson(value, Array<String>::class.java).toList() ?: emptyList()

    }
}