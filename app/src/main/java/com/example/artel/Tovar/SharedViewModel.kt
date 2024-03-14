package com.example.artel.Tovar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    val selectedTitle = MutableLiveData<String>()
    val selectedImgPath = MutableLiveData<String>()

    fun select(title: String, imgPath: String) {
        selectedTitle.value = title
        selectedImgPath.value = imgPath
    }
}