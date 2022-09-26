package com.edloca.fastask.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edloca.fastask.database.entities.TaskEntity
import com.edloca.fastask.network.RoomRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val roomRepository:RoomRepositoryImpl): ViewModel() {

    private val _taskInserted:MutableLiveData<TaskEntity> = MutableLiveData()
    val taskInserted:LiveData<TaskEntity> = _taskInserted

    private val _taskList:MutableLiveData<MutableList<TaskEntity>> = MutableLiveData()
    val taskList:LiveData<MutableList<TaskEntity>> = _taskList

    private val _deleteTask:MutableLiveData<Int> = MutableLiveData()
    val deleteTask:LiveData<Int> = _deleteTask

    private val _updateTask:MutableLiveData<TaskEntity?> = MutableLiveData()
    val updateTask:LiveData<TaskEntity?> = _updateTask


    fun addTask(taskName:String, taskContent:String){

        viewModelScope.launch(Dispatchers.IO){

            val id = roomRepository.addTask(taskName,taskContent)
            val lastTaskInserted = roomRepository.getTaskById(id)
            _taskInserted.postValue(lastTaskInserted)



        }

    }


    fun getAllTasks(){
        viewModelScope.launch(Dispatchers.IO){
            _taskList.postValue(roomRepository.getAllTasks())
        }
    }


    fun deleteTask(task:TaskEntity){
        viewModelScope.launch(Dispatchers.IO) {
            val result = roomRepository.deleteTask(task)
            if (result>0) _deleteTask.postValue(task.id) else _deleteTask.postValue(-1)
        }
    }


    fun updateTask(task: TaskEntity){
        viewModelScope.launch(Dispatchers.IO) {
            task.isReady = !task.isReady
            val result = roomRepository.updateTask(task)
            if (result>0)_updateTask.postValue(task) else _updateTask.postValue(null)
        }
    }

}