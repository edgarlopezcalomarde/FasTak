package com.edloca.fastask.ui.taskdetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.edloca.fastask.databinding.ActivityTaskDetailBinding
import com.edloca.fastask.ui.main.MainActivity
import com.edloca.fastask.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class TaskDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskDetailBinding
    private val taskDetailViewModel: TaskDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskDetailViewModel.getTask(intent.getIntExtra("taskId",0))

        taskDetailViewModel.task.observe(this, Observer {
            binding.etTitle.setText(it?.name.toString())
            binding.etContent.setText(it?.content.toString())

        })


        binding.btnSave.setOnClickListener {
            taskDetailViewModel.saveTask(
                binding.etTitle.text.toString(),
                binding.etContent.text.toString(),
                intent.getIntExtra("taskId",0)
            )
        }


        binding.btnShare.setOnClickListener {
            val intent= Intent().apply{
                action=Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,binding.etContent.text.toString())
                type="text/plain"
                putExtra(Intent.EXTRA_TITLE,binding.etTitle.text.toString())

            }

            val shareIntent=Intent.createChooser(intent,null)
            startActivity(shareIntent)
        }


        taskDetailViewModel.updateTask.observe(this, Observer {
            if (it > 0){
                Toast.makeText(this, "Task saved succesfully saving task", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Error saving task", Toast.LENGTH_SHORT).show()
            }
        })



    }


    override fun onBackPressed() {
       startActivity(Intent(this@TaskDetailActivity, MainActivity::class.java))
       finish()

    }
}