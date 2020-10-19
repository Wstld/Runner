package com.example.runner.data

class EmployeeRepository private constructor(private val employeeDao: EmployeeDao){
    fun addEmployee(employee: Employee){
        employeeDao.addEmployee(employee)
    }

    fun getEmployees() = employeeDao.getEmployees()

    companion object{
        @Volatile private var instance:EmployeeRepository? = null
        fun getInstance(employeeDao: EmployeeDao) = instance?: synchronized(this){
            instance ?: EmployeeRepository(employeeDao).also { instance = it }
        }
    }
}