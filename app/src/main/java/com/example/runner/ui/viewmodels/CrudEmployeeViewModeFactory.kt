package com.example.runner.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.runner.data.EmployeeRepository

class CrudEmployeeViewModeFactory(private val employeeRepository: EmployeeRepository):ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CrudEmployeeViewModel(employeeRepository) as T
    }
}
