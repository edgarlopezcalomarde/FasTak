package com.edloca.fastask.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edloca.fastask.database.entities.TaskEntity
import com.edloca.fastask.databinding.ActivityMainBinding
import com.edloca.fastask.ui.taskdetail.TaskDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), TasksAdapter.TaskListener {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    private var tasks: MutableList<TaskEntity> = mutableListOf()

    private lateinit var rvTasks:RecyclerView
    private lateinit var taskAdapter: TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getAllTasks()

        mainViewModel.taskList.observe(this, Observer {
            tasks.clear()
            tasks.addAll(it)
            rvTasks.adapter?.notifyDataSetChanged()
        })


        mainViewModel.deleteTask.observe(this){ id ->
            if(id != -1){
                val task = tasks.filter { it.id == id }[0]
                val pos = tasks.indexOf(task)
                tasks.removeAt(pos)
                rvTasks.adapter?.notifyItemRemoved(pos)
            }else{
                showToast("Error deleting task")
            }
        }

        mainViewModel.taskInserted.observe(this){
            tasks.add(it)
            rvTasks.adapter?.notifyItemInserted(tasks.size)
        }

        binding.btnAddTask.setOnClickListener {
            val taskName = binding.etTaskName.text.toString()

            if (taskName.isNotEmpty()){
                mainViewModel.addTask(taskName, "")
            } else{
                showToast("Debes establecer un nombre")
            }
        }

        setUpRvTasks()
    }



    private fun setUpRvTasks(){
        taskAdapter = TasksAdapter(this, tasks, this)
        rvTasks = binding.rvTasks
        rvTasks.setHasFixedSize(true)
        rvTasks.layoutManager =  GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        rvTasks.adapter = taskAdapter
    }

    private fun showToast(text:String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()


    override fun onTaskClick(task:TaskEntity) {
       var intent = Intent(this@MainActivity, TaskDetailActivity::class.java)
       intent.putExtra("taskId", task.id)
       startActivity(intent)
       finish()
    }

    override fun deleteTask(task:TaskEntity) {
        mainViewModel.deleteTask(task)
    }
}