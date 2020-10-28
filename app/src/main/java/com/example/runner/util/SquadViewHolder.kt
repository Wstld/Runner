package com.example.runner.util

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.runner.data.dto.Employee
import kotlinx.android.synthetic.main.employee_list_item.view.*

class SquadViewHolder(itemView: View, val listener: RecyclerViewItemClick): RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
    lateinit var employeeItem: Employee
    var currentPos: Int = 0
    init {
        itemView.setOnClickListener(this)
    }
    val idView: TextView = itemView.main_list_id
    val nameView: TextView = itemView.main_list_name
    fun bind(employee: Employee, position: Int){
        idView.text = employee.id.toString()
        nameView.text = employee.name
        employeeItem = employee
        currentPos = position
    }

    override fun onClick(view: View?) {
        listener.onItemClick(employeeItem,currentPos)
    }

    interface RecyclerViewItemClick{
        fun onItemClick(employee: Employee, position: Int){}
    }

}