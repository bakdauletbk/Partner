package kz.smartideagroup.partner.content.model.response.notification

import com.google.gson.annotations.SerializedName

data class RetailNotifications (
    @SerializedName("id")
    val id : Long? =null,
    @SerializedName("title")
    val title : String? = null,
    @SerializedName("description")
    val description : String? = null,
    @SerializedName("createdAt")
    val createdAt : String? = null
)