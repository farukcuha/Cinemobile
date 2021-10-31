package com.pandorina.cinemobile.util

import android.app.Activity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.pandorina.cinemobile.R

object CinemobileAd {
    fun getAdRequest(): AdRequest = AdRequest.Builder().build()

    fun showInterstitialAd(activity: Activity, recommendMovie: () -> Unit) {
        var mInterstitialAd: InterstitialAd?
        InterstitialAd.load(activity, activity.getString(R.string.ad_interstitial_unit_id), getAdRequest(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                    recommendMovie.invoke()
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    mInterstitialAd?.show(activity)
                    mInterstitialAd?.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                recommendMovie.invoke()
                            }

                            override fun onAdShowedFullScreenContent() {
                                mInterstitialAd = null
                            }
                        }
                }
            })
    }

}