package com.example.runner.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.runner.data.Employee
import com.example.runner.data.EmployeeRepository

class MainListViewModel(private val employeeRepository: EmployeeRepository):ViewModel() {
    fun addEmployee(employee: Employee) = employeeRepository.addEmployee(employee)
}