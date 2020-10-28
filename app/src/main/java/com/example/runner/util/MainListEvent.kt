package com.example.runner.util

import android.content.Context
import android.view.View

sealed class MainListEvent<out T> {
    class ListItemClicked<out Int>(val id:Int,val view:View):MainListEvent<Int>()
    class RemoveClicked<out Nothing>(val id: Int):MainListEvent<Nothing>()
}
