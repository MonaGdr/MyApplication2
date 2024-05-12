package com.example.myapplication

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent

const val NOTIFICATION_ID = "messageNotification"

@HiltAndroidApp
class ExampleApplication : Application(),
    Configuration.Provider {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface HiltWorkerFactoryEntryPoint {
        fun workerFactory(): HiltWorkerFactory
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        WorkManager.initialize(this, workManagerConfiguration)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_ID,
                "Irandoc_Messages",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override val workManagerConfiguration: Configuration =
        Configuration.Builder()
            .setWorkerFactory(
                EntryPoints.get(this, HiltWorkerFactoryEntryPoint::class.java).workerFactory()
            )
            .build()
}