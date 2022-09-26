package com.edloca.fastask.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edloca.fastask.R
import com.edloca.fastask.database.entities.TaskEntity

class TasksAdapter(
    val context:Context,
    val tasks: List<TaskEntity>,
    val listener: TaskListener
):RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    interface TaskListener{
        fun onTaskClick(task:TaskEntity)
        fun deleteTask(task:TaskEntity)
    }


    inner class TasksViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvReduceContent = itemView.findViewById<TextView>(R.id.tvReduceContent)

        val btnDelete = itemView.findViewById<ImageView>(R.id.btnDelete)

        fun bind(task:TaskEntity){
            tvTitle.text = task.name
            tvReduceContent.text = task.content

            itemView.setOnClickListener { listener.onTaskClick(task) }

            btnDelete.setOnClickListener { listener.deleteTask(task) }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
       val itemView = LayoutInflater.from(context).inflate(R.layout.task_box, parent, false)
        return  TasksViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = tasks.size

}