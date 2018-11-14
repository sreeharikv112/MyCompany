package com.lovoo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lovoo.ui.meetingdetails.model.BookRoom
import com.lovoo.utils.AppConstants

/**
 * Room initialization
 */
@Database(entities = arrayOf(BookRoom::class), version = 1, exportSchema = false)
abstract class BookingRoomDB : RoomDatabase(){

    abstract fun bookingItemAndBookingModel(): BookRoomDAO
    companion object {
        private var INSTANCE : BookingRoomDB? =null
        fun getInstance(context : Context):BookingRoomDB?{
            if(INSTANCE == null){
                synchronized(BookingRoomDB::class){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                        BookingRoomDB::class.java, AppConstants.BOOKING_ROOM_DB
                    ).build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance(){
            INSTANCE =null
        }
    }
}