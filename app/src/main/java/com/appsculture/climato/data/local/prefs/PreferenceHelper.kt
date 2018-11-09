package com.appsculture.climato.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class PreferenceHelper(val context: Context) {

    fun defaultPref(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun customPref(name: String): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    inline fun SharedPreferences.edit(operator: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operator(editor)
        editor.apply()
    }
}