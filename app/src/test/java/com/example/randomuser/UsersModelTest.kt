package com.example.randomuser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.randomuser.model.Address
import com.example.randomuser.model.BirthDay
import com.example.randomuser.model.Name
import com.example.randomuser.model.Picture
import com.example.randomuser.model.Street
import com.example.randomuser.model.User
import com.example.randomuser.model.UsersResponse
import com.example.randomuser.network.AppService
import com.example.randomuser.view_model.UsersViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.internal.schedulers.ExecutorScheduler.ExecutorWorker
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class UsersModelTest {
    @get: Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var appService: AppService

    @InjectMocks
    var userViewModel = UsersViewModel()

    private  var testSingle: Single<UsersResponse>? = null

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getUsersSuccess(){
        val usersResponse = UsersResponse(
            arrayListOf(User(
                name = Name(firstName = "testfirstName", lastName = "testlastName"),
                email = "testEmail",
                birthDay = BirthDay("1996-07-23T20:36:49.551Z", 23),
                address = Address(Street(1, "testStreet"), "testCity", "testState", "testCountry", 1800),
                cellPhone = "09753003215",
                picture = Picture("testImageUrl")
            ))
        )
//        val userList = usersResponse

        testSingle = Single.just(usersResponse)

        Mockito.`when`(appService.getUsers()).thenReturn(testSingle)

        userViewModel.refresh()

        Assert.assertEquals(1, userViewModel.users.value?.size)
        Assert.assertEquals(false, userViewModel.usersLoadError.value)
        Assert.assertEquals(false, userViewModel.loading.value)
    }

    @Test
    fun getUsersFail(){
        testSingle = Single.error(Throwable())

        Mockito.`when`(appService.getUsers()).thenReturn(testSingle)
        userViewModel.refresh()
        Assert.assertEquals(true, userViewModel.usersLoadError.value)
        Assert.assertEquals(false, userViewModel.loading.value)
    }

    @Before
    fun setUpRxSchedulers(){

        val immediate = object: Scheduler(){
            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                // set delay to 0
                return super.scheduleDirect(run, 0, unit)
            }
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, false)
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler{immediate}

    }
}