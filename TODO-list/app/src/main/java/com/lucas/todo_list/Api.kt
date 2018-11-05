package com.lucas.todo_list

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @POST("tasks")
    fun createTask(@Body body: AddTask): Call<ResponseBody>

    @GET("tasks")
    fun getTasks(): Call<List<Task>>

    @DELETE("tasks/{id}")
    fun delete(@Path("id") id: String): Call<ResponseBody>
}