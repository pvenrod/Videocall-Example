package com.paolo.examplevideocallapp.misc

import android.app.Activity
import com.appbrain.InterstitialBuilder
import com.appodeal.ads.Appodeal
import com.appodeal.ads.InterstitialCallbacks
import com.paolo.examplevideocallapp.BuildConfig
import com.paolo.examplevideocallapp.data.repository.AppRepository

interface AdsManager {
    fun initialize(activity: Activity)
    fun showInterstitial(
        activity: Activity,
        onAdClosed: () -> Unit,
    )
    fun loadInterstitial(
        activity: Activity,
    )
}

class AppodealAdsManager(
    private val appRepository: AppRepository,
): AdsManager {

    companion object {
        private const val appId = "2a9d198be1a140e3e9cdf8e522cf9b65c360acb13d3f8617"
        private const val adTypes = Appodeal.INTERSTITIAL or Appodeal.REWARDED_VIDEO or Appodeal.BANNER_BOTTOM
        private const val defaultTimeBetweenAds = 10000L
    }

    private var lastInterstitial = 0L
    private var interstitialBuilder: InterstitialBuilder? = null
    private var onInterstitialClosed: (() -> Unit)? = null

    override fun initialize(activity: Activity) {
        Appodeal.setTesting(BuildConfig.DEBUG)
        Appodeal.initialize(activity, appId, adTypes) {
            // crashlytics
        }
    }

    override fun showInterstitial(
        activity: Activity,
        onAdClosed: () -> Unit,
    ) {
        val appConfiguration = appRepository.appConfiguration
        if (appConfiguration.showAds == true) {
            onInterstitialClosed = onAdClosed
            if (System.currentTimeMillis() - lastInterstitial >= (appConfiguration.timeBetweenInterstitials ?: defaultTimeBetweenAds)) {
                Appodeal.setInterstitialCallbacks(object : InterstitialCallbacks {
                    override fun onInterstitialLoaded(isPrecache: Boolean) { }
                    override fun onInterstitialFailedToLoad() { }
                    override fun onInterstitialShown() {
                        lastInterstitial = System.currentTimeMillis()
                    }
                    override fun onInterstitialShowFailed() { }
                    override fun onInterstitialClicked() { }
                    override fun onInterstitialClosed() {
                        onAdClosed()
                    }
                    override fun onInterstitialExpired() { }
                })
                if (Appodeal.isLoaded(Appodeal.INTERSTITIAL)) {
                    Appodeal.show(activity, Appodeal.INTERSTITIAL)
                } else {
                    if (appConfiguration.showAppBrainAds == true) {
                        showAlternateInterstitial(activity, onAdClosed)
                    } else {
                        onAdClosed()
                    }
                }
            } else {
                onAdClosed()
            }
        } else {
            onAdClosed()
        }
    }

    override fun loadInterstitial(activity: Activity) {
        interstitialBuilder = InterstitialBuilder
            .create()
            .setOnDoneCallback {
                interstitialBuilder?.preload(activity)
                onInterstitialClosed?.invoke()
            }
            .preload(activity)
    }

    // region PRIVATE METHODS
    private fun showAlternateInterstitial(
        activity: Activity,
        onAdClosed: () -> Unit,
    ) {
        interstitialBuilder?.run {
            show(activity)
        } ?: onAdClosed()
    }
    // endregion

}