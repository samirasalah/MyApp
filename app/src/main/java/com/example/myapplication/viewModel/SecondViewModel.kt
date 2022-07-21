package com.example.myapplication.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.model.ItemApp

/**
 * Created by Samira Salah
 */
class SecondViewModel() : BaseViewModel() {
    var item=MutableLiveData<ItemApp>()
}
