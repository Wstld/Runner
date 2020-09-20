package com.example.runner

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Competence(var chief:Boolean=false,var driverTruck:Boolean=false,var driverLadder:Boolean=false,var searchAndRescueLeader:Boolean=false,var searchAndRescue: Boolean=false) :
    Parcelable {
    fun updateCompetence(){}
}