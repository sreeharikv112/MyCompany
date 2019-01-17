package com.myoffice.ui.homelist.model

import android.os.Parcel
import android.os.Parcelable
import com.myoffice.ui.factsdetail.model.CompanyFact
import com.squareup.moshi.Json


/**
 * model class for POJO
 */

data class OfficeRoom(val type: String,
                      val roomNumber: String,
                      val department: String,
                      val name: String,
                      val officeLevel: Int,
                      val id: String,
                      @field:Json(name = "CompanyFact") val companyFact: CompanyFact?) : Parcelable

 {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readParcelable(CompanyFact::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(roomNumber)
        parcel.writeString(department)
        parcel.writeString(name)
        parcel.writeInt(officeLevel)
        parcel.writeString(id)
        parcel.writeParcelable(companyFact, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OfficeRoom> {
        override fun createFromParcel(parcel: Parcel): OfficeRoom {
            return OfficeRoom(parcel)
        }

        override fun newArray(size: Int): Array<OfficeRoom?> {
            return arrayOfNulls(size)
        }
    }
}