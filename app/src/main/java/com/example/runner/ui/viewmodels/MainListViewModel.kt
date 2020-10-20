package com.example.runner.ui.viewmodels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.runner.data.DataSource
import com.example.runner.data.Employee
import com.example.runner.data.EmployeeRepository
import com.example.runner.ui.AddEmployeeActivity
import com.example.runner.ui.ID_KEY
import com.example.runner.ui.MainListActivity
import com.example.runner.util.MainListRecyclerAdapter
import com.example.runner.util.MainListViewHolder
import com.example.runner.util.RecyclerViewSpacing
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainListViewModel(private val employeeRepository: EmployeeRepository):ViewModel(),MainListViewHolder.OnItemClickListener
{
    lateinit var recyclerAdapter: MainListRecyclerAdapter
    fun addEmployee(employee: Employee) = employeeRepository.addEmployee(employee)
    fun getEmployees() = employeeRepository.getEmployees()

    fun getMainListRecyclerAdapter()= MainListRecyclerAdapter(this)
//On clicked Item in recycler view.
    override fun onItemClick(id: Int,view:View) {
    val showEmployee = Intent(view.context, AddEmployeeActivity::class.java)
    showEmployee.putExtra(ID_KEY,id)
    startActivity(view.context,showEmployee,showEmployee.extras)
    }

    //On clicked trashcan in recycler view.
    override fun onTrashClick(employee: Employee, view: View) {

        MaterialAlertDialogBuilder(view.context)
            .setTitle("Vill du tabort ${employee.name}")
            .setPositiveButton("Ja"){dialog,which ->
                val employeeName = employee.name
                employeeRepository.removeEmployee(employee)
                Toast.makeText(view.context, "$employeeName har tagits bort", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Nej"){dialog,which->
                dialog.cancel()
            }
            .show()

    }

    //search/filter logic for search input.
    fun filter(str:String){
        employeeRepository.filterList(str)
    }


}