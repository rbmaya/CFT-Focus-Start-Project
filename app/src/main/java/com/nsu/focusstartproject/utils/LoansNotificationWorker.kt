package com.nsu.focusstartproject.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.nsu.focusstartproject.R

class LoansNotificationWorker(private val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    private var notificationId = 1
    private val notificationManager = NotificationManagerCompat.from(context)

    companion object {
        const val CHANNEL_ID = "1"
        const val CHANNEL_NAME = "NOTIFICATION_CHANNEL"
        const val CHANNEL_DESC = "CHANNEL_DESCRIPTION"
    }

    override fun doWork(): Result {
        createNotificationChannel()

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.loan)
            .setContentTitle(context.getString(R.string.payment))
            .setContentText(context.getString(R.string.payment_notification_text))
            .build()

        notificationManager.notify(notificationId++, notification)

        return Result.success()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val descriptionText = CHANNEL_DESC
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
    }


}