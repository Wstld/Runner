package com.example.runner.ui.viewmodels

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runner.data.DataSource
import com.example.runner.data.Employee
import com.example.runner.data.EmployeeRepository
import com.example.runner.ui.AddEmployeeActivity
import com.example.runner.ui.ID_KEY
import com.example.runner.util.MainListEvent
import com.example.runner.util.MainListRecyclerAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
const val TAG = "D"
class MainListViewModel(private val employeeRepository: EmployeeRepository):ViewModel()
{

    fun getEmployees() = employeeRepository.getEmployees()
    fun handleEvent(event:MainListEvent<Int>){
        when (event){
            is MainListEvent.ListItemClicked<Int> -> { showEmployee(event.view,event.id)}
            is MainListEvent.RemoveClicked -> {removeEmployee(event.id)}
        }
    }

    private fun removeEmployee(id: Int) {
        val list = employeeRepository.getEmployees().value
        if (list != null ){
            val employee = list.find { it.id == id }!!
            employeeRepository.removeEmployee(employee)
        }else Log.d(TAG, "removeEmployee: DidntWOerk")
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