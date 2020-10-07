package com.example.runner

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.employee_list_item.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class MainListRecyclerAdapter(
    val  listener: MainListViewHolder.OnItemClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items:List<Employee> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MainListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.employee_list_item_w_remove,parent,false,),listener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MainListViewHolder -> {
                holder.apply {
                    bind(items[position],position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
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
    lateinit var thisEmployee:Employee

    init{
        itemView.setOnClickListener(this)
        trashBtn.setOnClickListener (this)
    }

    override fun onClick(view: View?) {

        if (view is ImageView){
            listener.onTrashClick(employeeId)
        }else{
            listener.onItemClick(employeeId)
        }
    }

    fun bind(employee: Employee,position: Int){
        mainListId.text = employee.id.toString()
        mainListName.text = employee.name
        employeeId = employee.id
        thisEmployee = employee
        empPostition = position

        //set images with if logic looking at competence. -->
    }
    interface OnItemClickListener{
        fun onItemClick(id: Int)
        fun onTrashClick(id: Int)
    }

}

