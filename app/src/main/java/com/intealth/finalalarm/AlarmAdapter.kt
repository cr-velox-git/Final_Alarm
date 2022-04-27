package com.intealth.finalalarm

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.intealth.finalalarm.AlarmAdapter.ViewHolder

class AlarmAdapter(private val alarmDataList: List<AlarmData>) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_alarm, parent)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(alarmDataList[position])
    }

    override fun getItemCount(): Int {
        return alarmDataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val alarmName: TextView
        private val alarmTime: TextView
        private val alarmFrequency: TextView

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        private val onOffAlarm: Switch

        init {
            alarmName = itemView.findViewById(R.id.itemViewAlarmName)
            alarmTime = itemView.findViewById(R.id.itemViewTime)
            alarmFrequency = itemView.findViewById(R.id.alarmViewFrequency)
            onOffAlarm = itemView.findViewById(R.id.itemViewAlarmNoOff)
        }

        @SuppressLint("SetTextI18n")
        fun setData(alarmData: AlarmData) {
            alarmName.text = alarmData.alarm_name
            alarmTime.text = alarmData.alarm_hour.toString() + ":" + alarmData.alarm_minute
            onOffAlarm.isChecked = alarmData.isAlarm_on_off

        }
    }
}
