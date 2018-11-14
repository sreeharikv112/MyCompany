package com.lovoo.ui.meetingdetails.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lovoo.R
import com.lovoo.ui.meetingdetails.model.BookRoom
import com.lovoo.utils.AppConstants
import java.text.SimpleDateFormat
import java.util.*

/**
 * Adapter class for showing meetings alreadt booked for specific room
 */
class MeetingListAdapter(var bookRoomList: List<BookRoom>, var onClickListener: View.OnClickListener)
    : RecyclerView.Adapter<MeetingListAdapter.MeetingListHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeetingListHolder {
        return MeetingListHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.booked_detail_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return bookRoomList.size
    }

    fun addMeeting(bookRoomList:List<BookRoom>){
        this.bookRoomList = bookRoomList
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: MeetingListHolder, position: Int) {
        val bookRoomModel: BookRoom = bookRoomList[position]
        holder.meetingRoomName.text = "Room Name : ${bookRoomModel.name}"
        holder.roomNumber.text= "Room # ${bookRoomModel.roomNumber}"
        holder.fromTime.text= convertLongToCalender(Date(bookRoomModel.fromTime))
        holder.toTime.text= convertLongToCalender(Date(bookRoomModel.toTime))
        holder.btnDelete.tag = bookRoomModel
        holder.btnDelete.setOnClickListener(onClickListener)
    }

    /**
     * Convert date to presentable format
     */
    fun convertLongToCalender(input: Date): String {
        var dateConvert: Date = input
        val myFormat = AppConstants.TIME_FORMAT
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        var  formated_time = sdf.format(dateConvert);
        return formated_time
    }

    class MeetingListHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        var meetingRoomName: TextView = itemView.findViewById(R.id.meetingRoomName)
        var roomNumber: TextView = itemView.findViewById(R.id.roomNumber)
        var fromTime: TextView = itemView.findViewById(R.id.fromTime)
        var toTime: TextView = itemView.findViewById(R.id.toTime)
        var btnDelete : Button = itemView.findViewById(R.id.deleteBtn)
    }
}