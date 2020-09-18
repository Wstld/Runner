package com.example.runner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list_main.*

class ListMain : AppCompatActivity(),MainListViewHolder.OnItemClickListener {
    lateinit var recyclerAdapter:MainListRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_main)
        initRecycler()
        addData()
    }

    fun initRecycler(){
        mainListRecycler.apply {
            layoutManager = LinearLayoutManager(this@ListMain)
            val spacing = RecyclerViewSpacing(30)
            addItemDecoration(spacing)
            recyclerAdapter = MainListRecyclerAdapter(this@ListMain)
            adapter = recyclerAdapter

        }
    }

    override fun onItemClick(name: String) {
        Toast.makeText(this, "det fungerar $name", Toast.LENGTH_SHORT).show()
    }

    fun addData(){
        val data = DataSource.createList()
        recyclerAdapter.submitList(data)
    }


}

