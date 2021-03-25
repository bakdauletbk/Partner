package kz.smartideagroup.partner.authorization.model.response

import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("accessToken")
    val accessToken: String? = null,
    @SerializedName("refreshToken")
    val refreshToken: String? = null
)