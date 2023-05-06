package com.example.pmrtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _appBarTitle = MutableLiveData("")
    val appBarTitle: LiveData<String> get() = _appBarTitle

    fun updateTitle(title: String) {
        _appBarTitle.value = title
    }

}