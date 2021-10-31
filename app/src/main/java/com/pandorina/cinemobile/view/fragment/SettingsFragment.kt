package com.pandorina.cinemobile.view.fragment

import android.content.Intent
import android.net.Uri
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.databinding.FragmentSettingsBinding
import com.pandorina.cinemobile.util.CinemobileAd
import com.pandorina.cinemobile.util.Util.loadImage
import com.pandorina.cinemobile.util.Util.setActionBarText
import com.pandorina.cinemobile.view.dialog.InfoDialogFragment

class SettingsFragment :
    BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate, null), View.OnClickListener {

    companion object {
        const val APP_URL = "https://play.google.com/store/apps/details?id=com.pandorina.cinemobile"
        const val MY_MAIL_ADDRESS = "ahmetfarukcuha@gmail.com"
    }

    override fun setUpViews() {
        setActionBarText(requireActivity(), getString(R.string.settings))
        binding.tvShare.setOnClickListener(this)
        binding.tvContact.setOnClickListener(this)
        binding.tvPlayStore.setOnClickListener(this)
        binding.tvHelp.setOnClickListener(this)
        binding.adSettings.root.loadAd(CinemobileAd.getAdRequest())
    }

    override fun observeData() {}

    override fun onClick(v: View?) {
        var intent: Intent? = null
        when (v?.id) {
            R.id.tv_contact -> {
                intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:$MY_MAIL_ADDRESS")
                }
            }
            R.id.tv_share -> {
                Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, APP_URL)
                    type = "text/plain"
                    intent = Intent.createChooser(this, null)
                }
            }
            R.id.tv_play_store -> {
                intent = Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(APP_URL) }
            }
            R.id.tv_help -> {
                InfoDialogFragment(R.string.help_dialog_txt).show(parentFragmentManager, "help_dialog")
            }
        }
        intent?.let { startActivity(it) }
    }
}