package com.example.runner.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.runner.R
import com.example.runner.data.Employee
import kotlinx.android.synthetic.main.employee_list_item.view.*

class MainListRecyclerAdapter(
    // receive logic for interface OnItemClickListener
    val  listener: MainListViewHolder.OnItemClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items:List<Employee> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MainListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.employee_list_item_w_remove,parent,false,),listener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Logic for multiple holders used on this adapter. Changes needed for onCLick function for multiple use.
        when(holder){
            is MainListViewHolder -> {
                holder.apply {
                    //binds Employee and forwards position to holder.
                    bind(items[position],position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    // sets items (via set or search) and notifies change.
    fun submitList(mainList:List<Employee>){
        items = mainList.sortedBy { it.lastRun }
        notifyDataSetChanged()
    }
    fun filterListOnSearch(list:List<Employee>){
        items = list
        notifyDataSetChanged()
    }

}

class MainListViewHolder(itemView: View,val listener: OnItemClickListener): RecyclerView.ViewHolder(itemView),
View.OnClickListener{
    val trashBtn:ImageView = itemView.findViewById<ImageView>(R.id.trashBtn)
    var employeeId: Int = 0
    var empPostition:Int = 0
    val mainListId:TextView = itemView.main_list_id
    val mainListName:TextView = itemView.main_list_name
    lateinit var thisEmployee: Employee

    init{
        // sets click listeners on these views to logic in onClick.
        itemView.setOnClickListener(this)
        trashBtn.setOnClickListener (this)
    }

    override fun onClick(view: View?) {
        // performs action depending on type of view that is clicked.
        if (view is ImageView){
            listener.onTrashClick(employeeId)
        }else{
            listener.onItemClick(employeeId)
        }
    }
    // gets employee from items, and updates inflated layout.
    fun bind(employee: Employee, position: Int){
        mainListId.text = employee.id.toString()
        mainListName.text = employee.name
        employeeId = employee.id
        thisEmployee = employee
        empPostition = position


    }
    interface OnItemClickListener{
        fun onItemClick(id: Int)
        fun onTrashClick(id: Int)
    }

}

