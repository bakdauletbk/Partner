package app.pillikan.kz.authorization.model.request

import com.google.gson.annotations.SerializedName

data class LoginBodyRequest(
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("userName")
    val userName: String? = null
)