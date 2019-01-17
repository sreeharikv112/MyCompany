package com.myoffice.ui.factsdetail.model

import android.os.Parcel
import android.os.Parcelable

/**
 * POJO class for CompanyFact
 */

data class CompanyFact (val title:String,
                        val images:List<String> = listOf(),
                        val text:String
                      ) : Parcelable

{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString()

    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeStringList(images)
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CompanyFact> {
        override fun createFromParcel(parcel: Parcel): CompanyFact {
            return CompanyFact(parcel)
        }

        override fun newArray(size: Int): Array<CompanyFact?> {
            return arrayOfNulls(size)
        }
    }
}

