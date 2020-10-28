package com.example.runner.ui.viewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runner.data.EmployeeRepository
import com.example.runner.data.dto.Employee
import com.example.runner.ui.AddEmployeeActivity
import com.example.runner.ui.ID_KEY
import com.example.runner.util.MainListEvent

const val TAG = "D"
class MainListViewModel(private val employeeRepository: EmployeeRepository):ViewModel()
{




    fun getEmployees(context:Context) = employeeRepository.getEmployeesFromFireStore(context)

    fun handleEvent(event:MainListEvent<Int>,context: Context){
        when (event){
            is MainListEvent.ListItemClicked<Int> -> { showEmployee(event.view,event.id)}
            is MainListEvent.RemoveClicked -> {removeEmployee(event.id,context)}
        }
    }

    private fun removeEmployee(id: Int,context: Context) {
        employeeRepository.deleteEmployeeFromFireStore(context,id)
    }
//On clicked Item in recycler view.


    //search/filter logic for search input.
    fun filter(str:String){
        employeeRepository.filterList(str)
    }

    private fun showEmployee(view: View,id:Int){
        val showEmployee = Intent(view.context,AddEmployeeActivity::class.java)
        showEmployee.putExtra(ID_KEY,id)
        startActivity(view.context,showEmployee,showEmployee.extras)
    }


}