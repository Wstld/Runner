package com.example.runner.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.runner.data.DataSource
import com.example.runner.data.Employee
import com.example.runner.databinding.ActivityListMainBinding
import com.example.runner.ui.viewmodels.MainListViewModel
import com.example.runner.util.InjectorUtil
import com.example.runner.util.MainListRecyclerAdapter
import com.example.runner.util.MainListViewHolder
import com.example.runner.util.RecyclerViewSpacing
import com.google.android.material.dialog.MaterialAlertDialogBuilder

//Main list activity. Holds functionality for Main list recyclerview.
class MainListActivity : AppCompatActivity() {
    lateinit var viewModel:MainListViewModel
    private lateinit var binding:ActivityListMainBinding
    lateinit var recyclerAdapter: MainListRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = InjectorUtil.provideMainListViewModelFactory()
        viewModel = ViewModelProvider(this,factory)
            .get(MainListViewModel::class.java)

      intiUi(binding,binding.mainListRecycler)

        //search input and on change listener.
        binding.searchTxt.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(str: Editable?) {
               // filter(str.toString())
            }
        })


    }

    fun intiUi (binding: ActivityListMainBinding,rAdapter:RecyclerView){
        rAdapter.apply {
            layoutManager = LinearLayoutManager(context)
            //adds spacing to recyclerview
            val spacing = RecyclerViewSpacing(30)
            addItemDecoration(spacing)
            //set adapter and click handling through activity.
            recyclerAdapter = viewModel.getMainListRecyclerAdapter()
            adapter = recyclerAdapter

        }
        viewModel.getEmployees().observe(this, Observer { employeeList ->
            recyclerAdapter.submitList(employeeList)
        })
    }


    //click handling interfaced through mainlist viewholder, employees selected through Id.
    //Item clicked opens change screen for employee.
/*    override fun onItemClick(id:Int) {
        val showEmployee = Intent(this, AddEmployeeActivity::class.java)
        showEmployee.putExtra(ID_KEY,id)
        startActivity(showEmployee)
    }*/

    //trashcan click removes selected employee.
    /*override fun onTrashClick(id: Int) {
        val employee: Employee? = DataSource.data.find { it.id == id }
        if(employee!= null ){
            val name = employee.name
        MaterialAlertDialogBuilder(this)
            .setTitle("Vill du tabort $name")
            .setPositiveButton("Ja"){dialog,which ->
                DataSource.data.remove(employee)
                recyclerAdapter.submitList(DataSource.data)
                Toast.makeText(this, "$name har tagits bort", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Nej"){dialog,which->
                dialog.cancel()
            }
            .show()
    }
    }*/
}

