package com.pandorina.cinemobile

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class Preferences(activity: Activity){
    companion object{
        const val CINEMOBILE_PREFERENCES = "cinemobile_preferences"
    }
    val sharedPreferences: SharedPreferences =
        activity.getSharedPreferences(CINEMOBILE_PREFERENCES, Context.MODE_PRIVATE)
    fun edit(): SharedPreferences.Editor =  sharedPreferences.edit()
}