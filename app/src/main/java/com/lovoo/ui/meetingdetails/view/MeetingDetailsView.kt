package com.lovoo.ui.meetingdetails.view

import com.lovoo.ui.homelist.model.OfficeRoom

/**
    Action in meeting details view
 */
interface MeetingDetailsView {

    fun setupData(officeRoomData: OfficeRoom)
}