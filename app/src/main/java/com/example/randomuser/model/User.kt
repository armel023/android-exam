package com.example.randomuser.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Name(
    @SerializedName("first")
    val firstName: String?,
    @SerializedName("last")
    val lastName: String?
)

data class Street(
    @SerializedName("number")
    val number: Number?,
    @SerializedName("name")
    val name: String?
)

data class Address(
    @SerializedName("street")
    val street: Street?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("state")
    val state: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("postcode")
    val postCode: Number?
)

data class BirthDay(
    @SerializedName("date")
    var date: String?,
    @SerializedName("age")
    var age: Int?
)

data class Picture(
    @SerializedName("large")
    val imageUrl: String?
)


data class User(
    @SerializedName("name")
    val name: Name?,
    @SerializedName("location")
    val address: Address?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("dob")
    val birthDay: BirthDay?,
    @SerializedName("cell")
    val cellPhone: String?,
    @SerializedName("picture")
    val picture: Picture?
)

data class UsersResponse(
    @SerializedName("results")
    val users: List<User>
)
