package com.example.runner.util

import androidx.recyclerview.widget.DiffUtil
import com.example.runner.data.Employee

class MainListDiffUtilCallback():DiffUtil.ItemCallback<Employee>() {
    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
      return oldItem.id == newItem.id
    }

}