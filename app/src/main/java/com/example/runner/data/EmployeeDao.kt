package com.example.runner.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.runner.data.dto.Employee

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

