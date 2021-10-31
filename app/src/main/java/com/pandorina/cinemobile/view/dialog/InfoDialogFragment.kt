package com.pandorina.cinemobile.view.dialog

import android.os.Bundle
import android.view.View
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.databinding.FragmentInfoDialogBinding

class InfoDialogFragment(private val infoText: Int) :
    BaseDialogFragment(R.layout.fragment_info_dialog) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentInfoDialogBinding.bind(view)
        binding.apply {
            btnDismissDialog.setOnClickListener { dialog?.dismiss() }
            tvInfoText.text = getString(infoText)
        }
    }
}