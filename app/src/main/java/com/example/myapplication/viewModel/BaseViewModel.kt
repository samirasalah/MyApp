package com.example.myapplication.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.remote.api.Resource
import com.example.myapplication.data.remote.response.Response


/**
 * Created by Samira Salah
 */
open class BaseViewModel: ViewModel() {
     val resourceLiveData: MutableLiveData<Resource<Response<*>>> =
        MutableLiveData()

}