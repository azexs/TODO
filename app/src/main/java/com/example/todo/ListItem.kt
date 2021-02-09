package com.example.todo

import android.os.Parcelable
import android.os.Parcel



class ListItem(var priority: Int,var title: String,var time:String,var img:Int): Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ){
        priority = parcel.readInt()
        title = parcel.readString()
        time = parcel.readString()
        img = parcel.readInt()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(priority)
        dest.writeInt(img)
        dest.writeString(title)
        dest.writeString(time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListItem> {
        override fun createFromParcel(parcel: Parcel): ListItem {
            return ListItem(parcel)
        }

        override fun newArray(size: Int): Array<ListItem?> {
            return arrayOfNulls(size)
        }
    }

}

