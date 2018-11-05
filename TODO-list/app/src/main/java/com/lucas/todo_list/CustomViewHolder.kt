package com.lucas.todo_list

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.view_todo.view.*

class CustomViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val todoItem = view.txtItem
}