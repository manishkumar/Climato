package com.appsculture.climato.data.local

import android.arch.persistence.room.TypeConverter
import com.appsculture.climato.model.*
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
    fun fromCity(value: City): String {
        val gson = Gson()
        val type = object : TypeToken<City>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCity(value: String): City {
        val gson = Gson()
        val type = object : TypeToken<City>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromCoordinate(value: Coordinate): String {
        val gson = Gson()
        val type = object : TypeToken<Coordinate>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCoordinate(value: String): Coordinate {
        val gson = Gson()
        val type = object : TypeToken<Coordinate>() {}.type
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