package com.example.new_kuz.database

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun fromListStringToString(intList: List<String>): String = intList.joinToString(",")
    @TypeConverter
    fun toListIntFromString(stringList: String): List<String> {
        val result = stringList.split(",").map { it.trim() }

        return result
    }
}