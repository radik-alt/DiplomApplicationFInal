package com.example.diplomapplication.ui.TimerUI

import androidx.lifecycle.ViewModel

class ViewModelTimer : ViewModel() {
    // работает ли таймер
    private var isEdit: Boolean = false
    // открыто ли приложение
    private var isOpen: Boolean = true

    public fun setIsEdit(edit: Boolean){
        isEdit = edit
    }

    public fun getIsEdit(): Boolean{
        return isEdit
    }

    public fun setOpen(edit: Boolean){
        isOpen = edit
    }

    public fun getOpne(): Boolean{
        return isOpen
    }
}