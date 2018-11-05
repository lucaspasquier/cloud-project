package com.lucas.todo_list

data class AddTask(val title: String, val description: String = "", val done: Boolean = false)
