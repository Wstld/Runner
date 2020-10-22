package com.example.runner.util

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.runner.data.Employee
import com.example.runner.databinding.EmployeeListItemWRemoveBinding

class MainListViewHolder(private val binding: EmployeeListItemWRemoveBinding, val listener: OnItemClickListener): RecyclerView.ViewHolder(binding.root),
    View.OnClickListener{
    var employeeId: Int = 0
    var empPostition:Int = 0
    lateinit var thisEmployee: Employee

    init{
        // sets click listeners on these views to logic in onClick.
        binding.recyclerViewItem.setOnClickListener(this)
        binding.trashBtn.setOnClickListener (this)
    }

    override fun onClick(view: View?) {
        // performs action depending on type of view that is clicked.
        if(view!=null) {
            when (view) {
                is ImageView -> listener.onTrashClick(thisEmployee, view)

                else -> listener.onItemClick(employeeId, view)
            }
        }
    }
    // gets employee from items, and updates inflated layout.
    fun bind(employee: Employee, position: Int){
        binding.mainListId.text = employee.id.toString()
        binding.mainListName.text = employee.name
        employeeId = employee.id
        empPostition = position
        thisEmployee = employee


    }
    interface OnItemClickListener{
        fun onItemClick(id: Int,view: View)
        fun onTrashClick(employee: Employee, view: View)
    }

}