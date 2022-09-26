package com.edloca.fastask.ui.taskdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edloca.fastask.database.entities.TaskEntity
import com.edloca.fastask.network.RoomRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class TaskDetailViewModel @Inject constructor(private val repository:RoomRepositoryImpl): ViewModel(){

    private val _task: MutableLiveData<TaskEntity?> = MutableLiveData()
    val task: LiveData<TaskEntity?> = _task


    private val _updateTask : MutableLiveData<Int> = MutableLiveData()
    val updateTask: LiveData<Int> = _updateTask

    fun getTask(id:Int){
        viewModelScope.launch (Dispatchers.IO){
            _task.postValue(repository.getTaskById(id.toLong()))
        }
    }

    fun saveTask(taskTitle:String, taskContent:String, taskId:Int){

        viewModelScope.launch (Dispatchers.IO){
            _updateTask.postValue(
                repository.updateTask(
                    TaskEntity(
                        id = taskId,
                        name = taskTitle,
                        content = taskContent,
                        lastModification = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    )
                )
            )


        }
    }
}