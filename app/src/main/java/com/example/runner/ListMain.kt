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

    override fun onItemClick(name: String) {
        Toast.makeText(this, "${name}", Toast.LENGTH_SHORT).show()
    }



}

