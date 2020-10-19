package com.example.runner.util

import com.example.runner.data.DataBase
import com.example.runner.data.EmployeeRepository
import com.example.runner.ui.viewmodels.MainListViewModelFactory

object InjectorUtil {
    fun provideMainListViewModelFactory():MainListViewModelFactory{
        val employeeRep = EmployeeRepository.getInstance(DataBase.getInstance().employeeDao)
        return MainListViewModelFactory(employeeRep)
    }
}