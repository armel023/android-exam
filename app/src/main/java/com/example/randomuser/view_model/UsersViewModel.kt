package com.example.randomuser.view_model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.randomuser.di.DaggerApiComponent
import com.example.randomuser.model.User
import com.example.randomuser.model.UsersResponse
import com.example.randomuser.network.AppService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.Period
import javax.inject.Inject

class UsersViewModel: ViewModel() {

    @Inject
    lateinit var appService: AppService

    init {
        DaggerApiComponent.create().inject(this)
    }
    private val disposable = CompositeDisposable()

    val users = MutableLiveData<List<User>>()
    val usersLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(){
        fetchUsers()
    }

    private fun fetchUsers(){

        loading.value = true

        disposable.add(
            appService.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<UsersResponse>() {

                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onSuccess(value: UsersResponse) {
                        users.value = retrieveBDayAndAge(value)
                        usersLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        usersLoadError.value = true
                        loading.value = false
                    }

                })
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun retrieveBDayAndAge(userResponse: UsersResponse): List<User>{
        if(userResponse.users.isNotEmpty()){
            userResponse.users.forEach{
                if(it.birthDay?.date?.isNotEmpty() == true){
                    val age = computeAge(it.birthDay.date!!)
                    it.birthDay.age = age
                    it.birthDay.date = formatBDay(it.birthDay.date!!)
                }

            }
        }
        return userResponse.users
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatBDay(birthDay: String): String{
        val birthDate = OffsetDateTime.parse(birthDay)
        return "${birthDate.month.toString().lowercase().replaceFirstChar { it.uppercase() }} ${birthDate.dayOfMonth}, ${birthDate.year}"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun computeAge(birthDay: String): Int{
        val dateTime = OffsetDateTime.parse(birthDay)
        val age = Period.between(dateTime.toLocalDate(), LocalDate.now()).years
        return age
    }
}