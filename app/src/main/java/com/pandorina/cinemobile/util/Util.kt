package com.pandorina.cinemobile.util

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity

object Util {


    val setActionBarText: (Activity, String) -> Unit = { activity, title ->
        (activity as AppCompatActivity).supportActionBar?.let { it.title = title}
    }

}