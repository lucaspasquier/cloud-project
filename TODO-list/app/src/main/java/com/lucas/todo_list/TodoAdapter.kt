package com.lucas.todo_list

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class TodoAdapter(private var items: MutableList<Task> = arrayListOf()) : RecyclerView.Adapter<CustomViewHolder>() {

    lateinit var calback: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(parent.inflate(R.layout.view_todo))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.todoItem.text = items[position].title

        holder.itemView.setOnClickListener {
            calback?.let {
                it.onItemClickListener(items[position])
            }
        }
    }

    fun setOnClickListener(listener: ClickListener) {
        this.calback = listener
    }

    fun notifyDataSetChanged(list: List<Task>?) {
        list.let {
            items.clear()
            items.addAll(ArrayList(list))

            this.notifyDataSetChanged()
        }
    }

    interface ClickListener {
        fun onItemClickListener(task: Task)
    }
}