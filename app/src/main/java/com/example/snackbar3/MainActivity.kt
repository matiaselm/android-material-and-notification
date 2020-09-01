package com.example.snackbar3

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.snackbar.Snackbar.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Integer.parseInt

/*  TODO: Clean up the code:
        - delete unused layouts
        - move onClick methods and notification builder to own functions
        - add translations
 */

class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID = "1234"

    private fun createNotificationChannel() {
        try {
            Log.d("snackbar3", "notificationChannel")
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = getString(R.string.channel_name)
                val descriptionText = getString(R.string.channel_description)
                val importance = NotificationManager.IMPORTANCE_DEFAULT

                val channel = NotificationChannel(
                    CHANNEL_ID,
                    name,
                    importance
                )
                    .apply {
                        description = descriptionText
                    }

                // Register the channel with the system
                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        } finally {
            Log.d("snackbar3", "some error creating notification channel")
        }
    }
    // Set the intent that will fire when the user taps the notification
    /*
    val myIntent = Intent(this, AlertDetails::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, myIntent, 0)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
    */

    private fun changeText(){
        // Switch-case to manage textView text
        when(helloTextView.text){
            getString(R.string.goodbye) -> helloTextView.text = getString(R.string.hello)
            getString(R.string.hello) -> helloTextView.text = getString(R.string.goodbye)
            else -> {
                helloTextView.text = getString(R.string.hello)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        createNotificationChannel()
        val builder = Notification.Builder(this, "1234")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(getString(R.string.notification_hello))
            .setContentText(getString(R.string.notification_message))
            .setPriority(Notification.PRIORITY_DEFAULT)

        val notification = builder.build()

        Log.d("snackbar3","onCreate before clicklistener")

        button_first.setOnClickListener { view -> make(
                    view,
                    getString(R.string.snackbar_msg),
                    LENGTH_LONG
            )
            .setAction("Action", null)
            .show()

            changeText()

            NotificationManagerCompat.from(this).notify(parseInt(CHANNEL_ID), notification)
        }
    }
}