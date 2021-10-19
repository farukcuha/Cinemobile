package com.pandorina.cinemobile.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.pandorina.cinemobile.R
import android.view.ViewGroup




abstract class BaseDialogFragment(@LayoutRes contentLayoutId: Int): DialogFragment(contentLayoutId) {
    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setWindowAnimations(R.style.dialog_scale_animation_style)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}