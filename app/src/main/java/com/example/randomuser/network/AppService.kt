package com.example.randomuser.network

import com.example.randomuser.di.DaggerApiComponent
import com.example.randomuser.model.UsersResponse
import io.reactivex.Single
import javax.inject.Inject

class AppService {

    @Inject
    lateinit var appApi: AppApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getUsers(): Single<UsersResponse>{
        return appApi.getUsers()
    }

}