package com.example.runner.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.runner.data.Employee
import com.example.runner.data.EmployeeRepository

class MainListViewModelFactory(private val employeeRepository: EmployeeRepository):ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainListViewModel(employeeRepository) as T
    }
}