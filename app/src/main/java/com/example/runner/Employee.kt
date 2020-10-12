package com.example.runner

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*


open class Employee(var id:Int, var name:String, var squad:Int, var lastRun:String ="", var competence:Competence){
    init {
        //Sets date and time of init as Lastrun.
        lastRun.ifEmpty { lastRun = getDate() }
    }
    fun addToList(){}
    fun removeFromList(){}
    fun getDate():String{
        // gets current time.
        val date = GregorianCalendar.getInstance()
        val formatter= SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return formatter.format(date.time)
    }
    fun setDate(date:String){
        lastRun = date
    }

}