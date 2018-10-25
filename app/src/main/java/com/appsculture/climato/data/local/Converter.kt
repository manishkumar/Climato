package com.appsculture.climato.data.local

import android.arch.persistence.room.TypeConverter
import com.appsculture.climato.model.Main
import com.appsculture.climato.model.Sys
import com.appsculture.climato.model.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromWeatherList(value: List<Weather>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Weather>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toWeatherList(value: String): List<Weather> {
        val gson = Gson()
        val type = object : TypeToken<List<Weather>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromWeather(value: Weather): String {
        val gson = Gson()
        val type = object : TypeToken<Weather>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toWeather(value: String): Weather {
        val gson = Gson()
        val type = object : TypeToken<Weather>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromSys(value: Sys): String {
        val gson = Gson()
        val type = object : TypeToken<Sys>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toSys(value: String): Sys {
        val gson = Gson()
        val type = object : TypeToken<Sys>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromMain(value: Main): String {
        val gson = Gson()
        val type = object : TypeToken<Main>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toMain(value: String): Main {
        val gson = Gson()
        val type = object : TypeToken<Main>() {}.type
        return gson.fromJson(value, type)
    }
}