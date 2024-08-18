package com.example.randomuser.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.randomuser.R
import com.example.randomuser.util.getProgressDrawable
import com.example.randomuser.util.loadImage

class FullDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_full_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // retrieve data passed from main activity
        val imageUrl = intent.getStringExtra("imageUrl")
        val userName = intent.getStringExtra("name")
        val userBirthDay = intent.getStringExtra("birthDay")
        val userAge = intent.getStringExtra("age")
        val userEmail = intent.getStringExtra("email")
        val userMobile = intent.getStringExtra("mobile")
        val userStreet = intent.getStringExtra("street")
        val userCity = intent.getStringExtra("city")
        val userState = intent.getStringExtra("state")
        val userCountry = intent.getStringExtra("country")
        val userPostCode= intent.getStringExtra("postCode")

        // get access for view components
        val imageView = findViewById<ImageView>(R.id.image_view_pic_large)
        val textViewName = findViewById<TextView>(R.id.text_view_name)
        val textViewBirthDay = findViewById<TextView>(R.id.text_view_birthday)
        val textViewAge = findViewById<TextView>(R.id.text_view_age)
        val textViewEmail= findViewById<TextView>(R.id.text_view_email)
        val textViewMobile = findViewById<TextView>(R.id.text_view_mobile)
        val textViewStreet = findViewById<TextView>(R.id.text_view_street)
        val textViewCity = findViewById<TextView>(R.id.text_view_city)
        val textViewState = findViewById<TextView>(R.id.text_view_state)
        val textViewCountry = findViewById<TextView>(R.id.text_view_country)
        val textViewPostCode = findViewById<TextView>(R.id.text_view_postcode)

        // load image
        val progressDrawable = getProgressDrawable(this)
        imageView.loadImage(imageUrl, progressDrawable)

        // set text value to view components
        textViewName.text = userName
        textViewBirthDay.text = userBirthDay
        textViewAge.text = userAge
        textViewEmail.text = userEmail
        textViewMobile.text = userMobile
        textViewStreet.text = userStreet
        textViewCity.text = userCity
        textViewState.text = userState
        textViewCountry.text = userCountry
        textViewPostCode.text = userPostCode

    }
}