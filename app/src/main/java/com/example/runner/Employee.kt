package com.example.runner

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

@Parcelize
open class Employee(val id:Int, val name:String, var squad:Int,var lastRun:GregorianCalendar = GregorianCalendar(), var competence:Competence) : Parcelable {
    fun addToList(){}
    fun removeFromList(){}
    fun getDate():String{

        val date = lastRun
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(date.time)
    }

}