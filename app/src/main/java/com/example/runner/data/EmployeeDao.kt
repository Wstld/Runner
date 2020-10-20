package com.example.runner.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class EmployeeDao {
    private val employeeList = mutableListOf<Employee>()
    private val employeeLiveList = MutableLiveData<List<Employee>>()

    init {
        employeeLiveList.value = employeeList
    }
    fun addEmployee(employee: Employee){
        employeeList.add(employee)
        employeeLiveList.value = employeeList
    }
    fun updateEmployee(id:Int,eName:String,eSquad:Int,eLastRun:String,eCompetence: List<Boolean>){
        //Add error checks.
        employeeList.find { it.id == id }.apply {
            if (this != null){
                //Set comp to CompentenceObject
                val comp = competence.apply {
                    chief = eCompetence[0]
                    driverTruck = eCompetence[1]
                    driverLadder = eCompetence[2]
                    searchAndRescueLeader = eCompetence[3]
                    searchAndRescue = eCompetence[4]
                }

                this.id = id
                name = eName
                squad = eSquad
                lastRun = eLastRun
                competence = comp

                employeeLiveList.value = employeeList
            }
        }
    }
    fun removeEmployee(employee: Employee){

            employeeList.remove(employee)
            employeeLiveList.value = employeeList

    }

    fun getEmployees() = employeeLiveList as LiveData<List<Employee>>

    fun getFilteredEmployees(string: String){
        val filteredList = mutableListOf<Employee>()
        employeeList.forEach { if (it.id.toString().toUpperCase().contains(string.toUpperCase())||it.name.toUpperCase().contains(string.toUpperCase())){filteredList.add(it)} }
        employeeLiveList.value = filteredList
    }
}

