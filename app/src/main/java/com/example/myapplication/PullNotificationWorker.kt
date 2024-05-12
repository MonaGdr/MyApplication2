package com.example.myapplication

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

@HiltWorker
class PullNotificationWorker @AssistedInject constructor(
    @Assisted val ctx: Context,
    @Assisted params: WorkerParameters,
//    private val notificationViewModel: NotificationViewModel
) : CoroutineWorker(ctx, params) {

    private val notificationManager =
        ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override suspend fun doWork(): Result {
        delay(10000)
        showBasicNotification()
        return Result.success()
    }

    private fun showBasicNotification() {
        val activityIntent = Intent(ctx, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            ctx,
            1,
            activityIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) PendingIntent.FLAG_IMMUTABLE else 0
        )
        val notification = NotificationCompat.Builder(ctx, NOTIFICATION_ID)
            .setContentTitle("water reminder")
            .setContentText("don't forget to drink water.")
            .setSmallIcon(androidx.core.R.drawable.notification_bg)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(
            1,
            notification
        )
    }
}