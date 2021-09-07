package com.e.commerce.ui.fragments.user.notifyservice

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.e.commerce.R
import com.e.commerce.ui.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

// Created by Hussein_Mohammad on 6/25/2021.
class FirebaseMessageReceiver : FirebaseMessagingService() {
    private var viewModel : NotificationsViewModel = NotificationsViewModel()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.notification != null) {
            showNotification(
                remoteMessage.notification!!.title,
                remoteMessage.notification!!.body
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
//        viewModel = ViewModelProvider(this@FirebaseMessageReceiver).get(NotificationsViewModel::class.java)
    }

    private fun customNotifyDesign(title: String?, message: String?): RemoteViews {
        val remoteView = RemoteViews(
            applicationContext.packageName, R.layout.notification_design
        )
        remoteView.setTextViewText(R.id.tv_title_notify, title)
        remoteView.setTextViewText(R.id.tv_body_notify, message)
        remoteView.setImageViewResource(R.id.img_notify, R.drawable.app_logo)
        return remoteView
    }

    @SuppressLint("ObsoleteSdkInt")
    fun showNotification(title: String?, message: String?) {
        val intent = Intent(this, MainActivity::class.java)
        val channelID = "notify_channel"
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_ONE_SHOT
        )

        var builder = NotificationCompat.Builder(applicationContext, channelID)
            .setSmallIcon(R.drawable.app_logo)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setContent(customNotifyDesign(title, message))
        } else {
            builder.setContentTitle(title).setContentText(message).setSmallIcon(R.drawable.app_logo)
        }

        val notifyManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT
            >= Build.VERSION_CODES.O
        ) {
            val notificationChannel = NotificationChannel(
                channelID, "web_app",
                NotificationManager.IMPORTANCE_HIGH
            )
            notifyManager.createNotificationChannel(
                notificationChannel
            )
        }

        notifyManager.notify(0, builder.build())
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }
}