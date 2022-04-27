package com.intealth.finalalarm;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {AlarmData.class}, version = 2, exportSchema = false)
public abstract class AlarmRoomDB extends RoomDatabase {

    //create database instance
    private static AlarmRoomDB database;
    //define database name
    private static String DATABASE_NAME = "database";
    private static String TAG = "AlarmRoomDB";

    public synchronized static AlarmRoomDB getInstance(Context context) {
        Log.d(TAG, "ROOM DB INITIATED");
        //check connection
        if (database == null) {
            //when data base is null
            //initialize database
            database = Room.databaseBuilder(context.getApplicationContext(), AlarmRoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build();
        }
        return database;
    }
    
    public abstract AlarmDao alarmDao();
}


