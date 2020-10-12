package com.example.runner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.employee_list_item.view.*

// --------------------- Basically same as MainListRecyclerAdapter ---------------------------------------

class SquadListRecyclerAdapter(
    val listener:SquadViewHolder.RecyclerViewItemClick
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
class SquadViewHolder(itemView:View,val listener: RecyclerViewItemClick):RecyclerView.ViewHolder(itemView),
View.OnClickListener{
    lateinit var employeeItem: Employee
    var currentPos: Int = 0
    init {
        itemView.setOnClickListener(this)
    }
    val idView:TextView = itemView.main_list_id
    val nameView:TextView = itemView.main_list_name
    fun bind(employee: Employee,position: Int){
        idView.text = employee.id.toString()
        nameView.text = employee.name
        employeeItem = employee
        currentPos = position
    }

    override fun onClick(view: View?) {
        listener.onItemClick(employeeItem,currentPos)
    }

    interface RecyclerViewItemClick{
        fun onItemClick(employee: Employee,position: Int){}
    }

}