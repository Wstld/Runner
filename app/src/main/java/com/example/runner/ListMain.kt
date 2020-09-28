package com.example.runner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_main.*
import java.text.FieldPosition

class ListMain : AppCompatActivity(),MainListViewHolder.OnItemClickListener {
    lateinit var recyclerAdapter:MainListRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = DataSource.data
        setContentView(R.layout.activity_list_main)
        initRecycler(data.sortedBy { it.lastRun })

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

    override fun onItemClick(position:Int) {
        val showEmployee = Intent(this,AddEmployeeActivity::class.java)
        showEmployee.putExtra(POSITION_KEY,position)
        startActivity(showEmployee)
    }



}

