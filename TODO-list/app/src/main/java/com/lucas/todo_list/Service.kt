package com.lucas.todo_list

import okhttp3.ResponseBody
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Service {
    private val service: Api

    companion object {
        const val BASE_URL = "http://ec2-18-224-183-117.us-east-2.compute.amazonaws.com/"  //1
//        const val BASE_URL = "http://186.212.109.182/"  //1
    }

    init {
        // 2
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL) //1
                .addConverterFactory(GsonConverterFactory.create()) //3
                .build()
        service = retrofit.create(Api::class.java) //4
    }

    fun getTasks(callback: Callback<List<Task>>) {
        val call = service.getTasks()
        call.enqueue(callback)
    }

    fun addTask(task: AddTask, callback: Callback<ResponseBody>) {
        val call = service.createTask(task)
        call.enqueue(callback)
    }

    fun deleteTask(id: String, callback: Callback<ResponseBody>) {
        val call = service.delete(id)
        call.enqueue(callback)
    }


}