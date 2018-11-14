package com.lovoo.ui.homelist.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lovoo.R
import com.lovoo.ui.homelist.model.OfficeRoom

/**
 * Adapter class for home list screen
 */
class ListOfRoomAdapter(officeRoomList: List<OfficeRoom>, listener: View.OnClickListener) :
    RecyclerView.Adapter<ListOfRoomAdapter.RoomViewHolder>(), Filterable {

    var mRoomList:List<OfficeRoom>
    var mRoomListFiltered:List<OfficeRoom>
    private val mListener: View.OnClickListener
    private val TAG : String = ListOfRoomAdapter::class.java.toString()

    init {
        this.mRoomList = officeRoomList
        this.mRoomListFiltered = officeRoomList
        this.mListener = listener
    }
    override fun getItemCount(): Int {
        return mRoomListFiltered.size
    }
    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val roomModel: OfficeRoom = mRoomListFiltered[position]
        holder.roomTitle.text = roomModel.name
        holder.roomDepartment.text= roomModel.department
        holder.roomType.text= roomModel.type
        holder.itemView.tag = roomModel
        holder.itemView.setOnClickListener(mListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        return RoomViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.note_item,
                parent,
                false
            )
        )
    }

    class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var roomTitle: TextView = itemView.findViewById(R.id.roomTitle)
        var roomDepartment: TextView = itemView.findViewById(R.id.roomDepartment)
        var roomType: TextView = itemView.findViewById(R.id.roomType)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    mRoomListFiltered = mRoomList
                } else {
                    val filteredList = ArrayList<OfficeRoom>()
                    try {
                        for(row in   mRoomList ){
                          if(row.department.toLowerCase().contains(charString) ||
                              row.type.toLowerCase().contains(charString)
                                  ){
                              filteredList.add(row)
                          }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG,"Exception while performFiltering "+e.toString())
                    }
                    mRoomListFiltered = filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = mRoomListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                try {
                    var roomListFiltered = filterResults.values as ArrayList<OfficeRoom>
                    if(roomListFiltered.isNotEmpty()){
                        mRoomListFiltered = filterResults.values as ArrayList<OfficeRoom>
                        notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    Log.e(TAG,"Exception while searching "+e.toString())
                }
            }
        }
    }
}