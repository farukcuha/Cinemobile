package com.pandorina.cinemobile.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.hardware.input.InputManager
import android.net.ConnectivityManager
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

object Util {
    val setActionBarText: (Activity, String) -> Unit = { activity, title ->
        (activity as AppCompatActivity).supportActionBar?.let { it.title = title}
    }

    class Keyboard(val context: Context){
        private val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        fun show(){
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        fun hide(windowToken: IBinder){
            imm.hideSoftInputFromWindow(windowToken, 0)
        }
    }

}