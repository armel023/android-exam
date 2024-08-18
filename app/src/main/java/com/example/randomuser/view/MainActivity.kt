package com.example.randomuser.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.randomuser.R
import com.example.randomuser.view_model.UsersViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: UsersViewModel
    private val usersAdapter = UserListAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this)[UsersViewModel::class.java]

        // get users data from view model
        viewModel.refresh()

        // set listener for swipe refresh
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.main)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }

        observeViewModel()
    }

    private fun observeViewModel(){

        val errorView = findViewById<TextView>(R.id.text_view_error)
        val loadingView = findViewById<ProgressBar>(R.id.progress_bar_loading)
        val userListView = findViewById<RecyclerView>(R.id.recycler_view_users)

        userListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = usersAdapter
        }

        usersAdapter.onItemClick = {
            //passed the user data and transition to full details activity
            val intent = Intent(this, FullDetailsActivity::class.java)
            intent.putExtra("imageUrl", it.picture?.imageUrl)
            intent.putExtra("name", "${it.name?.firstName} ${it.name?.lastName}")
            intent.putExtra("birthDay", "${it.birthDay?.date}")
            intent.putExtra("age", "${it.birthDay?.age}")
            intent.putExtra("email", "${it.email}")
            intent.putExtra("mobile", "${it.cellPhone}")
            intent.putExtra("street", "${it.address?.street?.number} ${it.address?.street?.name}")
            intent.putExtra("city", "${it.address?.city}")
            intent.putExtra("state", "${it.address?.state}")
            intent.putExtra("country", "${it.address?.country}")
            intent.putExtra("postCode", "${it.address?.postCode}")
            startActivity(intent)
        }

        //Display all user on the screen
        viewModel.users.observe(this) { users ->
            users?.let {
                userListView.visibility = View.VISIBLE
                usersAdapter.updateUsers(it)
            }
        }

        // Show Error if the loading of user failed
        viewModel.usersLoadError.observe(this) { isError ->
            isError?.let { errorView.visibility = if (it) View.VISIBLE else View.GONE }
        }

        // Show loading if fetching is still on progress
        viewModel.loading.observe(this) { isLoading ->
            isLoading?.let {
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    errorView.visibility = View.GONE
                    userListView.visibility = View.GONE
                }
            }
        }
    }
}