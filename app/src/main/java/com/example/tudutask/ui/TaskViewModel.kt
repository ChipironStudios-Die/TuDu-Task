package com.example.tudutask.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.tudutask.data.AppDatabase
import com.example.tudutask.model.Task
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskDao = AppDatabase.getDatabase(application).taskDao()
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks().asLiveData()

    fun getTask(id: Int): LiveData<Task?> {
        val taskLiveData = MutableLiveData<Task?>()
        viewModelScope.launch {
            taskLiveData.value = taskDao.getTaskById(id)
        }
        return taskLiveData
    }

    fun insert(task: Task) = viewModelScope.launch {
        taskDao.insertTask(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        taskDao.updateTask(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        taskDao.deleteTask(task)
    }
}
