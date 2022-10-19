package com.udacity

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action
    val CHANNEL_ID="channel_id"
    val CHANNEL_NAME="channel name"
    val NOTIFICATION_ID=0
    lateinit var GroupOfRadio:RadioGroup
    lateinit var Choice:RadioButton
    lateinit var NameOfFile:String
    lateinit var ToastMessgae:Toast
   lateinit var  downloadManager:DownloadManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        GroupOfRadio = findViewById(R.id.GroupOfRadio)
        notificationManager = ContextCompat.getSystemService(
            this,
            NotificationManager::class.java
        ) as NotificationManager
        createNotificationChannel()



        /*
       onnly check if the notifaction work
       val intent= Intent(this,DetailActivity::class.java)
        val pendingIntent= TaskStackBuilder.create(this).run{
            addNextIntentWithParentStack(intent)
            getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val notifiction=NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle("Awesom notification")
            .setContentText("This is content text")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()
        val notificationManger= NotificationManagerCompat.from(this)*/
        custom_button.setOnClickListener {
            //  download()
           val ButtonSelect:Int=GroupOfRadio!!.checkedRadioButtonId
            if(ButtonSelect==-1)  {
                         //non value check
                ToastMessgae=Toast.makeText(applicationContext,"Choose a Download",Toast.LENGTH_LONG)
                ToastMessgae.show()
            } else{
                custom_button.setState(ButtonState.Loading)
                Choice=findViewById(ButtonSelect)
                CheckButton(Choice)
            }
        }
    }
    fun CheckButton(view: View){
        if(view.id==R.id.choice1){
           URL="https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
            NameOfFile=R.id.choice1.toString()
            download()
        } else if(view.id==R.id.choice2){
            URL="https://codeload.github.com/bumptech/glide/zip/master"
            NameOfFile=R.id.choice2.toString()
            download()
        } else if(view.id==R.id.choice3){
            URL="https://codeload.github.com/square/retrofit/zip/master"
            NameOfFile=R.id.choice3.toString()
            download()
        }
    }
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            context?.let{
                notificationManager.MangeNotificatin(
                    "the Project 3 repository is downloaded",it,GetStatus(id!!),NameOfFile
                )
            }
            custom_button.setState(ButtonState.Completed)
        }
    }

    private fun download() {
        val request =
            DownloadManager.Request(Uri.parse(URL))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
         downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.
    }

    companion object {
        private  var URL =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val CHANNEL_ID = "channelId"
    }

//------------------------------------------channel notification----------------------------------------
    fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channel=NotificationChannel(CHANNEL_ID,CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor=Color.GREEN
                enableLights(true)
            }
      //      val manager= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
 //------------------------------------------------get Status of file------------------------------------------------//

 fun GetStatus(ID:Long):String{
     val query=DownloadManager.Query()
     query.setFilterById(ID)
     val cursor: Cursor=downloadManager.query(query)
     if(cursor.moveToFirst()){
          val Index=cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
         var Status=cursor.getInt(Index)
         if(Status==DownloadManager.STATUS_FAILED){
             return getString(R.string.successfull)
         } else{
             return getString(R.string.failed)
         }
     }
     return getString(R.string.failed)
 }
}
