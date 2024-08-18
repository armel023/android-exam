package com.example.randomuser.view


import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.randomuser.R
import com.example.randomuser.model.User
import com.example.randomuser.util.getProgressDrawable
import com.example.randomuser.util.loadImage

class UserListAdapter(var users: ArrayList<User>): RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    var onItemClick: ((User)-> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun updateUsers(newUsers: List<User>){
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder (LayoutInflater.from(parent.context).inflate(R.layout.user_card, parent, false))

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
       holder.bind(users[position])
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(users[position])
        }
    }

    class UserViewHolder(view: View): RecyclerView.ViewHolder(view){
        val imageView = view.findViewById<ImageView>(R.id.image_view_pic)
        val userName = view.findViewById<TextView>(R.id.text_view_name)
        val email =  view.findViewById<TextView>(R.id.text_view_email)
        val progressDrawable = getProgressDrawable(view.context)
        fun bind(user: User){
            Log.d("TAG", "${user.name?.firstName}")
            Log.d("TAG", "${user.picture?.imageUrl}")
            userName.text = user.name?.firstName ?: ""
            email.text = user.email
            imageView.loadImage(user.picture?.imageUrl, progressDrawable)
        }
    }
}