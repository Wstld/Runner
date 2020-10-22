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

        //creates viewmodel from factory via injectorutil.
        val factory = InjectorUtil.provideMainListViewModelFactory()
        viewModel = ViewModelProvider(this,factory)
            .get(MainListViewModel::class.java)

      //init UI components, and observe changes in the "Fake" Database.
      intiUi(binding)

        //search input and on change listener.
        binding.searchTxt.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(str: Editable?) {
               viewModel.filter(str.toString())
            }
        })
    }
    fun intiUi (binding: ActivityListMainBinding){
        binding.mainListRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            //adds spacing to recyclerview
            val spacing = RecyclerViewSpacing(30)
            addItemDecoration(spacing)

            recyclerAdapter = viewModel.getMainListRecyclerAdapter()
            adapter = recyclerAdapter

        }
        viewModel.getEmployees().observe(this, Observer { employeeList ->
            recyclerAdapter.submitList(employeeList)
        })
    }


}


