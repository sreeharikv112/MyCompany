package com.lovoo.ui.homelist.model

import android.os.Parcel
import android.os.Parcelable
import com.lovoo.ui.lovoofactdetail.model.LovooFact

/**
 * model class for POJO
 */
@Suppress("UNCHECKED_CAST")
data class OfficeRoom(val type: String,
                      val roomNumber: String,
                      val department: String,
                      val name: String,
                      val officeLevel: Int,
                      val id: String,
                      val lovooFact: LovooFact?) : Parcelable

 {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readParcelable(LovooFact::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(roomNumber)
        parcel.writeString(department)
        parcel.writeString(name)
        parcel.writeInt(officeLevel)
        parcel.writeString(id)
        parcel.writeParcelable(lovooFact, flags)
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