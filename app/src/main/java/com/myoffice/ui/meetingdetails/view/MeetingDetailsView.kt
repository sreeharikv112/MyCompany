package com.myoffice.ui.meetingdetails.view

import com.myoffice.ui.homelist.model.OfficeRoom

/**
    Action in meeting details view
 */
interface MeetingDetailsView {

    fun setupData(officeRoomData: OfficeRoom)
}