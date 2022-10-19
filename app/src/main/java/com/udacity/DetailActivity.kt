package com.udacity

import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color.green
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
   private lateinit var notificationManger:NotificationManager
   private lateinit var Name:TextView
   private lateinit var Status:TextView
   private lateinit var btn:Button
   private lateinit var NameOffile:String
   private lateinit var StatusOffile:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        Name=findViewById(R.id.Name)
        Status=findViewById(R.id.Status)
        btn=findViewById(R.id.btn)
    //    notificationManger=ContextCompat.getSystemService(this,NotificationManager::class.java) as NotificationManager
      //  notificationManger.CancelAllNotification()

        //----------------------------get extras-------------//
         val Data=intent.extras
        NameOffile=Data?.getString("NAME_OF_FILE")!!
        StatusOffile= Data.getString("STATUS_OF_FILE")!!
        Name.text=NameOffile
        Status.text=StatusOffile
        //change the color of text according to the status of file
        if(StatusOffile==getString(R.string.successfull)){
            Status.setTextColor(getColor(R.color.green))
        }else{
            Status.setTextColor(getColor(R.color.red))
        }
   //use button to navigate to MainActivity

        val intent= Intent(applicationContext,MainActivity::class.java)
  btn.setOnClickListener {
    startActivity(intent)
  }

    }

}
