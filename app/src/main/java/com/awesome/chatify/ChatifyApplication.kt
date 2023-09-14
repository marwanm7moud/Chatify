package com.awesome.chatify

import android.app.Application
import android.util.Log
import com.quickblox.auth.session.QBSettings
import dagger.hilt.android.HiltAndroidApp
import kotlin.math.log

const val TAG = "TAG"

@HiltAndroidApp
class ChatifyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initQuickBloxSdk()
    }
    fun initQuickBloxSdk() {
        QBSettings.getInstance().init(
            getApplicationContext(),
            BuildConfig.APPLICATION_ID,
            BuildConfig.AUTHORIZATION_KEY,
            BuildConfig.AUTHORIZATION_SECRET
        )
        QBSettings.getInstance().setAccountKey(BuildConfig.ACCOUNT_KEY)
        QBSettings.getInstance().setAutoCreateSession(true)
        QBSettings.getInstance().isEnablePushNotification = true
    }
}