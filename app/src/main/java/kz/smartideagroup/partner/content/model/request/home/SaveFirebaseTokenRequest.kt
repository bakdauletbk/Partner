package kz.smartideagroup.partner.content.model.request.home

import com.google.gson.annotations.SerializedName

data class SaveFirebaseTokenRequest(
    @SerializedName("fireBaseToken")
    val fireBaseToken: String? = null
)