package com.lovoo.ui.meetingdetails.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * POJO class for Room Book
 */
@Entity
data class BookRoom(@PrimaryKey(autoGenerate = true)
                    var idpk:Int?,
                    var id:String?,
                    var name:String,
                    var roomNumber:String,
                    var fromTime:Long,
                    var toTime:Long
                    ){
    constructor():this(null,"","","",0L,0L)
}