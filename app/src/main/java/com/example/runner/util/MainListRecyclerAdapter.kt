package com.example.runner.util

import android.database.DatabaseUtils
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.runner.R
import com.example.runner.data.Employee
import com.example.runner.databinding.EmployeeListItemWRemoveBinding
import kotlinx.android.synthetic.main.employee_list_item.view.*

class MainListRecyclerAdapter(
    // receive logic for interface OnItemClickListener
    val  listener: MainListViewHolder.OnItemClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items:List<Employee> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return MainListViewHolder(
            EmployeeListItemWRemoveBinding.inflate(
                inflater,
                parent,
                false
            ),
            listener
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

class MainListViewHolder(private val binding:EmployeeListItemWRemoveBinding, val listener: OnItemClickListener): RecyclerView.ViewHolder(binding.root),
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
        fun onTrashClick(employee: Employee,view: View)
    }

}

