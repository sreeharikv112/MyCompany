package com.myoffice.ui.meetingdetails.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myoffice.R
import com.myoffice.ui.base.BaseActivity
import com.myoffice.utils.AppUtils
import androidx.lifecycle.Observer
import com.google.android.material.button.MaterialButton
import com.myoffice.ui.homelist.model.OfficeRoom
import com.myoffice.ui.meetingdetails.adapter.MeetingListAdapter
import com.myoffice.ui.meetingdetails.model.BookRoom
import com.myoffice.ui.meetingdetails.viewmodel.RoomListModelViewFactory
import com.myoffice.ui.meetingdetails.viewmodel.RoomListViewModel
import com.myoffice.utils.AppConstants

import kotlinx.android.synthetic.main.activity_meeting_detail.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Displays details of meeting room with already booked slots if any
 * Shows option to book meeting
 * Validates time across already booked slots
 * Books the specific slot by making entry to Room
 */
class MeetingDetailActivity : BaseActivity(), View.OnClickListener,
    MeetingDetailsView {

    private lateinit var roomName: TextView
    private lateinit var roomNumber: TextView
    private lateinit var fromHolder: TextView
    private lateinit var bookFrom: ImageView
    private lateinit var bookTo: ImageView
    private var mHour: Int = 0
    private var mMinute: Int = 0
    lateinit var fromCalenderTime : Calendar
    lateinit var toCalenderTime : Calendar
    private lateinit var btnBook : MaterialButton
    private val TAG:String = MeetingDetailActivity::class.java.toString()
    private var bookingDifferenceMins : Int = 15
    private var bookingDifferenceHrs : Int = 1
    @Inject
    lateinit var mAppUtils : AppUtils
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mListAdapter: MeetingListAdapter
    private lateinit var roomListViewModel: RoomListViewModel
    private lateinit var officeRoomData: OfficeRoom
    private lateinit var mSelectedBooking: BookRoom

    override fun onCreate(savedInstanceState: Bundle?) {
        getInjectionComponent().inject(this)
        super.onCreate(savedInstanceState)
        officeRoomData= intent.extras.get(AppConstants.DATA) as OfficeRoom
        renderView()
        init()
        setupData(officeRoomData)
    }


    override fun renderView() {
        setContentView(R.layout.activity_meeting_detail)
    }

    override fun init() {
        setSupportActionBar(toolbar)
        title = getString(R.string.book_meeting_room)
        roomName = findViewById(R.id.roomName)
        roomNumber = findViewById(R.id.roomNumber)
        fromHolder = findViewById(R.id.fromHolder)
        bookFrom = findViewById(R.id.bookFrom)
        bookTo = findViewById(R.id.bookTo)
        btnBook = findViewById(R.id.btnBookRoom)
    }

    override fun setupData(officeRoomData: OfficeRoom) {
        bookFrom.setOnClickListener (clickListener)
        bookTo.setOnClickListener (clickListener)
        btnBook.setOnClickListener (clickListener)
        roomName.text = officeRoomData.name
        roomNumber.text = (officeRoomData.roomNumber)
        val c1 = Calendar.getInstance()
        mHour = c1.get(Calendar.HOUR_OF_DAY)
        mMinute = c1.get(Calendar.MINUTE)
        c1.set(0, 0, 0, mHour, mMinute, 0)
        fromCalenderTime =c1
        toCalenderTime=c1
        mRecyclerView = findViewById(R.id.bookingListRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mListAdapter = MeetingListAdapter(
            bookRoomList = ArrayList<BookRoom>(),
            onClickListener = this
        )
        mRecyclerView.adapter = mListAdapter
        roomListViewModel = ViewModelProviders.of(this,
            RoomListModelViewFactory(application, officeRoomData.id)
        ).get(
            RoomListViewModel::class.java)
        roomListViewModel.mRoomList.observe(this@MeetingDetailActivity, Observer { bookRoom -> mListAdapter.addMeeting(bookRoom) })
    }

    /**
     * Shows time picker for starting time
     */
    private fun onFromTimePick(boolean: Boolean){
        var c = Calendar.getInstance()
        mHour = c.get(Calendar.HOUR_OF_DAY)
        mMinute = c.get(Calendar.MINUTE)
        val newFragment = TimePickerFragment()
        var bundle = Bundle()
        bundle.putBoolean(AppConstants.FROM,boolean)
        newFragment.arguments = bundle
        newFragment.show(fragmentManager, getString(R.string.time_picker))
    }

    /**
     * Checks if time is valid
     *
     */
    private fun validateTimeSelected(){
        if(fromCalenderTime.time.after(toCalenderTime.time)){
            showToast(getString(R.string.booking_time_invalid))
            return
        }else{
            var dateDifference =mAppUtils.getDateDifference(fromCalenderTime.time,toCalenderTime.time)
            if( (dateDifference.hours >= bookingDifferenceHrs) ){
                proceedWithDBCheck()
            }
            else if(dateDifference.minutes >=bookingDifferenceMins){
                proceedWithDBCheck()
            }
            else{
                showToast("Minimum $bookingDifferenceMins minutes difference should be there between times")
            }
        }
    }

    /**
     * Cross check with Room if any booking alredy present which overlaps the new entry
     */
    private fun proceedWithDBCheck(){
        var currentList: List<BookRoom> = roomListViewModel.mRoomList.value!!
        var shouldNotBook:Boolean = false
        for(checkValues in currentList){

            var isValid = isTimeValid(fromCalenderTime.getTimeInMillis(),
                toCalenderTime.getTimeInMillis(),
                checkValues.fromTime,
                checkValues.toTime)
            if(!isValid){
                shouldNotBook=true
                break;
            }
        }
        if(!shouldNotBook){
            var roomBookingModel = BookRoom()
            roomBookingModel.id=officeRoomData.id
            roomBookingModel.name=officeRoomData.name
            roomBookingModel.roomNumber=officeRoomData.roomNumber
            roomBookingModel.fromTime=fromCalenderTime.timeInMillis
            roomBookingModel.toTime=toCalenderTime.timeInMillis
            roomListViewModel.addNote(roomBookingModel)
            showToast(getString(R.string.meeting_saved))
        }else{
            showToast(getString(R.string.slot_already_booked))
        }
    }

    /**
     * Checks time validity for existing time entry
     */
    fun isTimeValid(_newFrom : Long, _newTo : Long, _savedFrom: Long, _savedTo: Long):Boolean{
        var status: Boolean = false
        val newFrom = convertLongToCalender(_newFrom)
        val newTo = convertLongToCalender(_newTo)
        val savedFrom = convertLongToCalender(_savedFrom)
        val savedTo = convertLongToCalender(_savedTo)
        if((newFrom.before(savedFrom) && newTo.before(savedFrom)) || (newFrom.after(savedTo) && newTo.after(savedTo))){
            status = true
        }
        return status
    }

    /**
     * Converts long value to calender value
     */
    fun convertLongToCalender(input: Long):Calendar{
        val newFrom = Calendar.getInstance()
        newFrom.timeInMillis = input
        return newFrom
    }

    override fun onClick(v: View?) {
        try {
            mSelectedBooking = v?.tag as BookRoom
            showAlert(getString(R.string.delete_confirmation),R.string.ok_option, R.string.cancel_option)
        } catch (e: Exception) {
            Log.d(TAG, "Exception onClick == ${e.toString()}")
        }
    }

    val clickListener = View.OnClickListener {view ->
        when (view.id) {
            R.id.bookFrom -> onFromTimePick(true)
            R.id.bookTo -> onFromTimePick(false)
            R.id.btnBookRoom -> validateTimeSelected()
        }
    }

    override fun handlePositiveAlertCallBack() {
        roomListViewModel.deleteNote(mSelectedBooking)
        showToast(getString(R.string.meeting_slot_deleted))
    }

}
