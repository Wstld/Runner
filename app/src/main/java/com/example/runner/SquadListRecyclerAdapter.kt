package com.example.runner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.employee_list_item.view.*

class SquadListRecyclerAdapter():RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = listOf<Employee>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SquadViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.employee_list_item,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
      when(holder){
         is SquadViewHolder -> holder.apply {
             bind(list.get(position))
         }
      }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(sortedList:List<Employee>){
        list = sortedList
    }

    inner class SquadViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val idView:TextView = itemView.main_list_id
        val nameView:TextView = itemView.main_list_name
        fun bind(employee: Employee){
            idView.text = employee.id.toString()
            nameView.text = employee.name
        }
    }
}