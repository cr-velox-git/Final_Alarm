package com.intealth.finalalarm

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.intealth.finalalarm.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var addAlarmDialog: Dialog
    private var picker: MaterialTimePicker? = null
    private var calendar: Calendar? = null
    var alarmDataList: List<AlarmData> = ArrayList()
    lateinit var database: AlarmRoomDB
    var TAG: String = "Main Activity"
    private var manager: AlarmManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        //creating the notification channel
        createNotificationChannel()

        //Initialize database
        database = AlarmRoomDB.getInstance(this)
        database.alarmDao().all

        //Add alarm function
        addAlarmDialog()

        //set up the recycle view


        binding.addAlarm.setOnClickListener {
            addAlarmDialog.show()
        }

        setContentView(view)
    }

    @SuppressLint("SetTextI18n")
    private fun addAlarmDialog() {
        // setting up Weight Dialog
        addAlarmDialog = Dialog(this)
        addAlarmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        addAlarmDialog.setContentView(R.layout.dialog_create_alarm)
        val alarmName: EditText = addAlarmDialog.findViewById(R.id.dialodAlarmName)
        val alarmTime: TextView = addAlarmDialog.findViewById(R.id.dialog_time)
        val alarmDone: Button = addAlarmDialog.findViewById(R.id.dialog_done)
        val alarmCbDaily: CheckBox = addAlarmDialog.findViewById(R.id.dialog_cb_daily)
        val alarmCbFifteen: CheckBox = addAlarmDialog.findViewById(R.id.dialog_cb_15)
        val alarmCbHalfDay: CheckBox = addAlarmDialog.findViewById(R.id.dialog_cb_half_day)
        val alarmCbHour: CheckBox = addAlarmDialog.findViewById(R.id.dialog_cb_hour)
        val alarmCbHalfHour: CheckBox = addAlarmDialog.findViewById(R.id.dialog_cb_half_hour)
        val alarmCb3Hour: CheckBox = addAlarmDialog.findViewById(R.id.dialog_cb_3_hour)
        val alarmCb2Hour: CheckBox = addAlarmDialog.findViewById(R.id.dialog_cb_2_hour)
        val alarmCb13Hour: CheckBox = addAlarmDialog.findViewById(R.id.dialog_cb_1_30_hour)

        var name: String
        var millis: Long = 0
        var hour: Long = 0
        var minute: Long = 0
        var frequency: Long = 0

        alarmTime.setOnClickListener {
            picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setTitleText("select Alarm Time")
                .build()
            picker!!.show(supportFragmentManager, "TAG")
            picker!!.addOnPositiveButtonClickListener(View.OnClickListener {
                hour = picker!!.hour.toLong()
                minute = picker!!.minute.toLong()
                calendar = Calendar.getInstance()
                calendar!!.set(Calendar.DAY_OF_MONTH, 0)
                calendar!!.set(Calendar.HOUR, picker!!.hour)
                calendar!!.set(Calendar.MINUTE, picker!!.minute)
                millis = calendar!!.timeInMillis
                Toast.makeText(this@MainActivity, "$hour : $minute : $millis", Toast.LENGTH_SHORT)
                    .show()

                if (hour < 13) {
                    alarmTime.text = "$hour:$minute Am"
                } else {
                    alarmTime.text = (hour - 12).toString() + ": $minute Pm"
                }
            })
        }

        alarmDone.setOnClickListener {
            name = alarmName.text.toString()
            if (alarmCbDaily.isChecked) {
                frequency = AlarmManager.INTERVAL_DAY
            } else if (alarmCbFifteen.isChecked) {
                frequency = AlarmManager.INTERVAL_FIFTEEN_MINUTES
            } else if (alarmCbHalfDay.isChecked) {
                frequency = AlarmManager.INTERVAL_HALF_DAY
            } else if (alarmCbHour.isChecked) {
                frequency = AlarmManager.INTERVAL_HOUR
            } else if (alarmCbHalfHour.isChecked) {
                frequency = AlarmManager.INTERVAL_HALF_HOUR
            } else if (alarmCb3Hour.isChecked) {
//todo
            } else if (alarmCb2Hour.isChecked) {
//todo
            } else if (alarmCb13Hour.isChecked) {
                frequency = 10000
            }

            if (setAlarmManager(name, hour, minute, millis, frequency)) {
                addAlarmDialog.dismiss()
            }
        }
    }

    private fun setAlarmManager(
        name: String,
        hour: Long,
        minute: Long,
        millis: Long,
        frequency: Long
    ): Boolean {
        Toast.makeText(
            this,
            "name: $name millis: $millis frequency: $frequency",
            Toast.LENGTH_SHORT
        ).show()


        val data = AlarmData()
        //Set data
        data.id = name
        data.alarm_name = name
        data.alarm_hour = hour
        data.alarm_minute = minute
        data.alarm_frequency = frequency
        //Insert data in database
        //Insert data in database
        database.alarmDao().insert(data)

        calendar = Calendar.getInstance()
        calendar!!.set(Calendar.DAY_OF_MONTH, 0)
        calendar!!.set(Calendar.HOUR, picker!!.hour)
        calendar!!.set(Calendar.MINUTE, picker!!.minute)

        val millis = calendar!!.timeInMillis

        Toast.makeText(this@MainActivity, "$hour : $minute : $millis", Toast.LENGTH_SHORT).show()

        manager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this@MainActivity, AlarmReceiver::class.java)
        intent.putExtra("TITLE", name)
        intent.putExtra("DES", name)
        intent.putExtra("NOTIFICATION_ID", frequency)
        val pendingIntent =
            PendingIntent.getBroadcast(this@MainActivity, 0, intent, PendingIntent.FLAG_MUTABLE)

        manager!!.setRepeating(
            AlarmManager.RTC_WAKEUP,
            millis,
            frequency,
            pendingIntent
        )

        //manager!!.setInexactRepeating(AlarmManager.RTC_WAKEUP,millis,frequency,pendingIntent)




        return true
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "alarm 1"
            val des = "description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("CHANNEL_ID", name, importance)
            channel.description = des
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}