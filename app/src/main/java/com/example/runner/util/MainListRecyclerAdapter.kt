package com.example.runner.util

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.runner.R
import com.example.runner.data.dto.Employee
import com.example.runner.databinding.EmployeeListItemWRemoveBinding
import kotlinx.android.synthetic.main.employee_list_item.view.*
import kotlinx.android.synthetic.main.employee_list_item.view.main_list_id
import kotlinx.android.synthetic.main.employee_list_item.view.main_list_name
import kotlinx.android.synthetic.main.employee_list_item_w_remove.view.*

class MainListRecyclerAdapter(val event:MutableLiveData<MainListEvent<Int>> = MutableLiveData()):ListAdapter<Employee,MainListRecyclerAdapter.MainListViewHolder>(MainListDiffUtilCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MainListViewHolder(
            inflater.inflate(R.layout.employee_list_item_w_remove,parent,false)
        )
    }

    override fun onBindViewHolder(holder: MainListViewHolder, position: Int) {
        getItem(position).let {
            holder.name.text = it.name
            holder.id.text = it.id.toString()

            holder.itemView.setOnClickListener {view ->
                event.value = MainListEvent.ListItemClicked(getItem(position).id,view)
            }
            holder.removeBtn.setOnClickListener{view ->
                event.value = MainListEvent.RemoveClicked(it.id)
            }
        }
    }

    class MainListViewHolder(root:View): RecyclerView.ViewHolder(root){
        var name: TextView = root.main_list_name
        var id: TextView = root.main_list_id
        var removeBtn: ImageView = root.trashBtn
    }
}




