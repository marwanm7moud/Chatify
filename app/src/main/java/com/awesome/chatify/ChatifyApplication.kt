package com.awesome.chatify

import android.Manifest
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.awesome.repository.RemoteDataSource
import com.awesome.ui.MainActivity
import com.quickblox.auth.session.QBSettings
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log
import kotlin.random.Random

const val TAG = "TAG"

@HiltAndroidApp
class ChatifyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initQuickBloxSdk()
        NotificationUtil.notificationListner(this)
    }

    private fun initQuickBloxSdk() {
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