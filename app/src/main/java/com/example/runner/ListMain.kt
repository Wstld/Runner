package com.example.runner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_list_main.*
import java.text.FieldPosition

class ListMain : AppCompatActivity(),MainListViewHolder.OnItemClickListener {
    lateinit var recyclerAdapter:MainListRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = DataSource.data

        setContentView(R.layout.activity_list_main)
        initRecycler(data.sortedBy { it.lastRun })
        val searchInput:EditText = findViewById(R.id.searchTxt)
        searchInput.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(str: Editable?) {
                filter(str.toString())
            }
        })



    }
    fun filter(str:String){
        val filteredList = mutableListOf<Employee>()
        DataSource.data.forEach { if (it.id.toString().toUpperCase().contains(str.toUpperCase())||it.name.toUpperCase().contains(str.toUpperCase())){filteredList.add(it)} }
        recyclerAdapter.filterListOnSearch(filteredList.toList())
    }

    fun initRecycler(data:List<Employee>){
        mainListRecycler.apply {
            layoutManager = LinearLayoutManager(this@ListMain)
            val spacing = RecyclerViewSpacing(30)
            addItemDecoration(spacing)
            recyclerAdapter = MainListRecyclerAdapter(this@ListMain)
            recyclerAdapter.submitList(data)
            adapter = recyclerAdapter

        }
    }

    override fun onItemClick(id:Int) {
        val showEmployee = Intent(this,AddEmployeeActivity::class.java)
        showEmployee.putExtra(ID_KEY,id)
        startActivity(showEmployee)
    }

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

