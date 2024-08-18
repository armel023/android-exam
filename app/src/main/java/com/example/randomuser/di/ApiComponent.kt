package com.example.randomuser.di

import com.example.randomuser.network.AppService
import com.example.randomuser.view_model.UsersViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: AppService)

    fun inject(viewModel: UsersViewModel)
}