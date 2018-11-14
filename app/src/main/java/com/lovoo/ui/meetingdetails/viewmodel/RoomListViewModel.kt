package com.lovoo.ui.meetingdetails.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.lovoo.db.BookingRoomDB
import com.lovoo.db.DBUtils
import com.lovoo.ui.meetingdetails.model.BookRoom

/**
 * Viewmodel for existing entry in booking slot
 */
class RoomListViewModel: AndroidViewModel {
    lateinit var mRoomList: LiveData<List<BookRoom>>
    var mBookRoomDB: BookingRoomDB = BookingRoomDB.getInstance(getApplication())!!


    constructor(application: Application, param: String):super(application)   {
        mRoomList = mBookRoomDB.bookingItemAndBookingModel().getAllBookingsWithID(param)
    }

    fun deleteNote(roomModel: BookRoom){
        val dbUtils = DBUtils()
        dbUtils.deleteNoteItem(mBookRoomDB,roomModel)
    }

    fun addNote(bookRoom: BookRoom){
        var dbUtils= DBUtils()
        dbUtils.addBooking(mBookRoomDB,bookRoom)
    }
}
