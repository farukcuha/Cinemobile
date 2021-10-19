package com.pandorina.cinemobile.view.dialog

import android.os.Bundle
import android.view.View
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.databinding.FragmentInfoDialogBinding

class InfoDialogFragment: BaseDialogFragment(R.layout.fragment_info_dialog) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentInfoDialogBinding.bind(view)
        binding.btnDismissDialog.setOnClickListener { dialog?.dismiss() }
    }
}