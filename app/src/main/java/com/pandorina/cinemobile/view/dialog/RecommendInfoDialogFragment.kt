package com.pandorina.cinemobile.view.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.databinding.FragmentRecommendInfoDialogBinding

class RecommendInfoDialogFragment: DialogFragment(R.layout.fragment_recommend_info_dialog) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentRecommendInfoDialogBinding.bind(view)
        binding.btnDismissDialog.setOnClickListener { dialog?.dismiss() }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setWindowAnimations(R.style.dialog_scale_animation_style)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}