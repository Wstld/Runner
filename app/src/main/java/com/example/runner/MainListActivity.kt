package com.example.runner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.runner.databinding.ActivityListMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

//Main list activity. Holds functionality for Main list recyclerview.
class MainListActivity : AppCompatActivity(),MainListViewHolder.OnItemClickListener {
    lateinit var recyclerAdapter:MainListRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = DataSource.data
        val binding = ActivityListMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycler(data.sortedBy { it.lastRun },binding.mainListRecycler)

        //search input and on change listener.
        binding.searchTxt.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(str: Editable?) {
                filter(str.toString())
            }
        })



    }
    //search/filter logic for search input.
    fun filter(str:String){
        val filteredList = mutableListOf<Employee>()
        DataSource.data.forEach { if (it.id.toString().toUpperCase().contains(str.toUpperCase())||it.name.toUpperCase().contains(str.toUpperCase())){filteredList.add(it)} }
        recyclerAdapter.filterListOnSearch(filteredList.toList())
    }


    fun initRecycler(data:List<Employee>,rAdapter:RecyclerView){
        // mainListRecycler is located in activity_list_main.xml
        rAdapter.apply {
            layoutManager = LinearLayoutManager(this@MainListActivity)
            //adds spacing to recyclerview
            val spacing = RecyclerViewSpacing(30)
            addItemDecoration(spacing)
            //set adapter and click handling through activity.
            recyclerAdapter = MainListRecyclerAdapter(this@MainListActivity)
            recyclerAdapter.submitList(data)
            adapter = recyclerAdapter

        }
    }
    //click handling interfaced through mainlist viewholder, employees selected through Id.
    //Item clicked opens change screen for employee.
    override fun onItemClick(id:Int) {
        val showEmployee = Intent(this,AddEmployeeActivity::class.java)
        showEmployee.putExtra(ID_KEY,id)
        startActivity(showEmployee)
    }

    //trashcan click removes selected employee.
    override fun onTrashClick(id: Int) {
        val employee:Employee? = DataSource.data.find { it.id == id }
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
    }
}

