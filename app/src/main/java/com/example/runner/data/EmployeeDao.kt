package com.example.runner.data

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
    fun removeEmployee(id:Int){
        employeeList.find { it.id == id }!!.removeFromList()
        employeeLiveList.value = employeeList
    }

    fun getEmployees(){
        employeeLiveList as LiveData<List<Employee>>
    }
}
