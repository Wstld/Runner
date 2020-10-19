package com.example.runner.ui.viewmodels

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.runner.data.Employee
import com.example.runner.data.EmployeeRepository
import com.example.runner.ui.AddEmployeeActivity
import com.example.runner.ui.ID_KEY
import com.example.runner.util.MainListRecyclerAdapter
import com.example.runner.util.MainListViewHolder
import com.example.runner.util.RecyclerViewSpacing

class MainListViewModel(private val employeeRepository: EmployeeRepository):ViewModel()
{
    lateinit var recyclerAdapter: MainListRecyclerAdapter
    fun addEmployee(employee: Employee) = employeeRepository.addEmployee(employee)
    fun getEmployees() = employeeRepository.getEmployees()

    fun initRecycler(data:List<Employee>, rAdapter: RecyclerView, context: Context,listner:MainListViewHolder.OnItemClickListener){
        // mainListRecycler is located in activity_list_main.xml
        rAdapter.apply {
            layoutManager = LinearLayoutManager(context)
            //adds spacing to recyclerview
            val spacing = RecyclerViewSpacing(30)
            addItemDecoration(spacing)
            //set adapter and click handling through activity.
            recyclerAdapter = MainListRecyclerAdapter(listner)
            recyclerAdapter.submitList(data)
            adapter = recyclerAdapter

        }
    }


}