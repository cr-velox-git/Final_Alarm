package com.intealth.finalalarm;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AlarmDao {
    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(AlarmData alarmData);

    //Delete query
    @Delete
    void delete(AlarmData alarmData);

    //Delete All
    @Delete
    void reset(List<AlarmData> alarmData);

    //Update query
    @Query("UPDATE alarmdata SET alarm_name = :sAlarmName, alarm_hour = :sAlarmHour,alarm_minute = :sAlarmMinute,alarm_frequency = :sAlarmFrequency,alarm_on_off = :sOnOff WHERE ID = :sID")
    void update(String sID, String sAlarmName, long sAlarmHour, long sAlarmMinute, String sAlarmFrequency, boolean sOnOff );


    //Get All Data Query
    @Query("SELECT*FROM alarmdata")
    List<AlarmData> getAll();
}
