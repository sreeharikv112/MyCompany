package com.myoffice.db

import com.myoffice.ui.meetingdetails.model.BookRoom
import org.jetbrains.anko.doAsync

/**
 * Utility class for Room
 */
class DBUtils {

    /**
     * Deletes @BookRoom item
     */
    fun deleteNoteItem(bookingRoomDB:BookingRoomDB,bookingRoom: BookRoom){
        doAsync {
            bookingRoomDB.bookingItemAndBookingModel().deleteBooking(bookingRoom)
        }
    }

    /**
     * Adds @BookRoom item
     */
    fun addBooking(bookingRoomDB:BookingRoomDB,bookingRoom: BookRoom){
        doAsync {
            bookingRoomDB.bookingItemAndBookingModel().insertBooking(bookingRoom)
        }
    }
}