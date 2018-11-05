package com.lucas.todo_list

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import retrofit2.Callback

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity(), TodoAdapter.ClickListener {

    val service = Service()
    val adapter = TodoAdapter()
    lateinit var taskEddt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            showCreateListDialog()
        }

        taskEddt = EditText(this)

        adapter.setOnClickListener(this)
        rcvTodo.layoutManager = LinearLayoutManager(this)
        rcvTodo.adapter = adapter

        service.getTasks(readCallback)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClickListener(task: Task) {
        AlertDialog.Builder(this)
                .setTitle("Deletar tarefa?")
                .setMessage("Deseja deletar a tarefa?")
                .setPositiveButton(android.R.string.ok, { dialog, i ->
                    service.deleteTask(task._id, deleteCallback)
                    dialog.dismiss()
                })
                .setNegativeButton("Cancelar", null)
                .create()
                .show()
    }

    private fun showCreateListDialog() {
        taskEddt.inputType = InputType.TYPE_CLASS_TEXT
        taskEddt.hint = getString(R.string.name_of_item)

        AlertDialog.Builder(this)
                .setTitle(R.string.create_item)
                .setView(taskEddt)
                .setPositiveButton(android.R.string.ok, { dialog, i ->
                    val title = taskEddt.text.toString()
                    service.addTask(AddTask(title), addCallback)
                    dialog.dismiss()
                })
                .create()
                .show()
    }

    private val addCallback = object : Callback<ResponseBody> {
        override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
            Log.e("MainActivity", "Problem calling API", t)
        }

        override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
            response?.isSuccessful.let {
                service.getTasks(readCallback)
            }
        }
    }

    private val readCallback = object : Callback<List<Task>> {
        override fun onFailure(call: Call<List<Task>>?, t: Throwable?) {
            Log.e("MainActivity", "Problem calling API", t)
        }

        override fun onResponse(call: Call<List<Task>>?, response: Response<List<Task>>?) {
            response?.isSuccessful.let {
                adapter.notifyDataSetChanged(response?.body())
            }
        }
    }

    private val deleteCallback = object : Callback<ResponseBody> {
        override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
            Log.e("MainActivity", "Problem calling API", t)
        }

        override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
            response?.isSuccessful.let {
                service.getTasks(readCallback)
            }
        }
    }
}
