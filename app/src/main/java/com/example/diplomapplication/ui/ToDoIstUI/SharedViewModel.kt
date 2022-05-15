package com.example.diplomapplication.ui.ToDoIstUI

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diplomapplication.room.task.Task

class SharedViewModel : ViewModel() {

    // хрнаиться model Task
    val chooseTask = MutableLiveData<Task>() // MutableLiveData схожее с LiveData
    // смотрим редактируем мы или доавляем задачу
    private var isEdit: Boolean = false

    fun selecteItem(task: Task){
        chooseTask.value = task
    }

    public fun setIsEdit(edit: Boolean){
        isEdit = edit
    }

    public fun getIsEdit(): Boolean{
        return isEdit
    }
}