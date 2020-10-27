package com.example.runner.util

import android.view.View
import com.example.runner.data.Employee

sealed class MainListEvent<out T> {
    class ListItemClicked<out Int>(val id:Int,val view:View):MainListEvent<Int>()
    class RemoveClicked<out Nothing>(val id: Int):MainListEvent<Nothing>()
}
