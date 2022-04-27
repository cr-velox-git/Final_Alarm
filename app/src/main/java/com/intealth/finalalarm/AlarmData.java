package com.intealth.finalalarm;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "AlarmData")
public class AlarmData implements Serializable {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "ID")
    private String ID;

    @ColumnInfo(name = "alarm_name")
    private String alarm_name;

    @ColumnInfo(name = "alarm_hour")
    private long alarm_hour;

    @ColumnInfo(name = "alarm_minute")
    private long alarm_minute;

    @ColumnInfo(name = "alarm_on_off")
    private boolean alarm_on_off;

    @ColumnInfo(name = "alarm_frequency")
    private Long alarm_frequency;

    public AlarmData() {
        ID = "";
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAlarm_name() {
        return alarm_name;
    }

    public void setAlarm_name(String alarm_name) {
        this.alarm_name = alarm_name;
    }

    public long getAlarm_hour() {
        return alarm_hour;
    }

    public void setAlarm_hour(long alarm_hour) {
        this.alarm_hour = alarm_hour;
    }

    public long getAlarm_minute() {
        return alarm_minute;
    }

    public void setAlarm_minute(long alarm_minute) {
        this.alarm_minute = alarm_minute;
    }

    public boolean isAlarm_on_off() {
        return alarm_on_off;
    }

    public void setAlarm_on_off(boolean alarm_on_off) {
        this.alarm_on_off = alarm_on_off;
    }

    public Long getAlarm_frequency() {
        return alarm_frequency;
    }

    public void setAlarm_frequency(Long alarm_frequency) {
        this.alarm_frequency = alarm_frequency;
    }
}
