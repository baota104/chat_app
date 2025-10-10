package com.example.chatapp.modal

import android.os.Parcel
import android.os.Parcelable

data class RecentChat(
        val friendid: String? = "",
        val name: String? = "",
        val time:String? = "",
        val friendsimage: String? = "",
        val sender :String? = "",
        val message:String? = "",
        val person: String ? = "",
        val status:String? = ""


):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(friendid)
        parcel.writeString(name)
        parcel.writeString(time)
        parcel.writeString(friendsimage)
        parcel.writeString(sender)
        parcel.writeString(message)
        parcel.writeString(person)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecentChat> {
        override fun createFromParcel(parcel: Parcel): RecentChat {
            return RecentChat(parcel)
        }

        override fun newArray(size: Int): Array<RecentChat?> {
            return arrayOfNulls(size)
        }
    }
}