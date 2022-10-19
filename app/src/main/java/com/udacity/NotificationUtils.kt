package com.udacity

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

private val NOTIFICATION_ID=0
private val REQUEST_CODE=0
private val FLAGS=0
val CHANNEL_ID="channel_id"
val CHANNEL_NAME="channel name"
val Notification_title="Udacity: Android Kotlin Nanodegree "
val Notification_Button="See Download Status"

//exsetinsion function
fun NotificationManager.MangeNotificatin(BodyOfmessage: String, applicationContext: Context,StatusofFile:String,NameofFile:String) {

    val intent= Intent(applicationContext,DetailActivity::class.java) //intent of Detail fragment would loaded by File Name and status of file
    intent.putExtra("STATUS_OF_FILE",StatusofFile)
    intent.putExtra("NAME_OF_FILE",NameofFile)

    // pending intent for Notification
    val pendingIntent=PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
 //Builder Of Notification
    val BuilderOfNotification=NotificationCompat.Builder(
        applicationContext, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentTitle(Notification_title)
        .setContentText(BodyOfmessage)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .addAction(R.drawable.ic_assistant_black_24dp, Notification_Button,pendingIntent)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
     notify(NOTIFICATION_ID,BuilderOfNotification.build())


}
fun NotificationManager.CancelAllNotification(){
    cancelAll()
}
