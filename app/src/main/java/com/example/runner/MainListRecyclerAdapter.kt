package com.example.runner

import android.app.Application
import android.content.ContentProvider
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.employee_list_item.view.*

class MainListRecyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items:MutableList<Employee> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MainListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.employee_list_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MainListViewHolder -> {
                holder.apply {
                    bind(items.get(position))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun submitList(mainList:MutableList<Employee>){
        items = mainList
    }

}

class MainListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
View.OnClickListener{
    val mainListId:TextView = itemView.main_list_id
    val mainListName:TextView = itemView.main_list_name
    init{
        itemView.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        TODO("Not yet implemented")
    }

    fun bind(employee: Employee){
        mainListId.text = employee.id.toString()
        mainListName.text = employee.name

        //set images with if logic looking at competence. -->
    }

}

