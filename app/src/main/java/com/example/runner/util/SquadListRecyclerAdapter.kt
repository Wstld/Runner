package com.example.runner.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.runner.R
import com.example.runner.data.dto.Employee

// --------------------- Basically same as MainListRecyclerAdapter ---------------------------------------

class SquadListRecyclerAdapter(
    val listener: SquadViewHolder.RecyclerViewItemClick
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = mutableListOf<Employee>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SquadViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.employee_list_item,parent,false),listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
      when(holder){
         is SquadViewHolder -> holder.apply {
             bind(list[position],position)
         }
      }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(sortedList:List<Employee>){
        list = sortedList.toMutableList()
    }
    fun addEmployeeToList(employee: Employee){
        if(list.contains(employee)){
            return
        }else{list.add(employee)
            list.sortBy { it.lastRun }}

    }

}
