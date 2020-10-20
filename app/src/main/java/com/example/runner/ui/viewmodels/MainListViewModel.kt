package com.example.runner.ui.viewmodels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

import androidx.lifecycle.ViewModel
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
    employeeRepository.removeEmployee(employee)

    }

    //search/filter logic for search input.
    fun filter(str:String){
        val filteredList = mutableListOf<Employee>()
        DataSource.data.forEach { if (it.id.toString().toUpperCase().contains(str.toUpperCase())||it.name.toUpperCase().contains(str.toUpperCase())){filteredList.add(it)} }
        recyclerAdapter.filterListOnSearch(filteredList.toList())
    }


}