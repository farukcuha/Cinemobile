package com.pandorina.cinemobile.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.IBinder
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.pandorina.cinemobile.R

object Util {
    val setActionBarText: (Activity, String) -> Unit = { activity, title ->
        (activity as AppCompatActivity).supportActionBar?.let { it.title = title}
    }

    class Keyboard(val context: Context){
        private val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        fun show(){
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        fun hide(windowToken: IBinder) {
            imm.hideSoftInputFromWindow(windowToken, 0)
        }
    }

    fun ImageView.loadImage(url: String?) {
        Glide.with(context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.image_place_holder)
            .into(this)
    }

    fun getPathByTitle(context: Context, title: String?): String? = context.run {
        when (title) {
            getString(R.string.popular) -> Constant.PATH_POPULAR
            getString(R.string.top_rated) -> Constant.PATH_TOP_RATED
            getString(R.string.now_playing) -> Constant.PATH_NOW_PLAYING
            getString(R.string.upcoming) -> Constant.PATH_UPCOMING
            else -> null
        }
    }

    class Comparator<T> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }

    fun View.navigate(destinationId: Int, argumentTag: String?, argument: Any?) {
        val bundle = argumentTag?.run {
            bundleOf(this to argument)
        }
        findNavController().navigate(destinationId, bundle, navOptions {
            anim {
                enter = R.anim.slide_in
                exit = R.anim.fade_out
                popEnter = R.anim.fade_in
                popExit = R.anim.slide_out
            }
        })
    }

}