package com.example.myapplication.io

import com.example.myapplication.data.remote.api.Resource
import com.example.myapplication.data.remote.response.Response
import com.example.myapplication.io.repository.UserRepository
import com.example.myapplication.model.ItemApp

/**
 * Created by Samira Salah
 */
interface ApiInteractor {
    suspend fun getData(page:Int):Resource<Response<ItemApp?>>
}

class UserInteractorImp(val apiRepository: UserRepository):ApiInteractor{
    override suspend fun getData(page:Int):Resource<Response<ItemApp?>>{
        return apiRepository.getData(page)
    }

}