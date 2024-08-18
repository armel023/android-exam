package com.example.randomuser.network

import com.example.randomuser.model.User
import com.example.randomuser.model.UsersResponse
import io.reactivex.Single
import retrofit2.http.GET

interface AppApi {

    @GET("api/?results=5000&inc=name,dob,email,cell,location,picture&noinfo")
    fun getUsers(): Single<UsersResponse>

}