package com.myoffice.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.myoffice.ui.meetingdetails.model.BookRoom

/**
 * DAO interface for Room
 * Handles all operations
 */
@Dao
interface BookRoomDAO {

    @Query("SELECT * FROM BookRoom")
    fun getAllBookings() : LiveData<List<BookRoom>>

    @Query("SELECT * FROM BookRoom WHERE id = :id")
    fun getAllBookingsWithID(id: String ) : LiveData<List<BookRoom>>

    @Query("SELECT * FROM BookRoom WHERE id = :id")
    fun getBookingById(id: String ): BookRoom

    @Insert
    fun insertBooking(bookRoom: BookRoom)

    @Delete
    fun deleteBooking(bookRoom: BookRoom)
}