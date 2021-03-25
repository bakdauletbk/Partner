package kz.smartideagroup.partner.content.model.response.notification

import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("list")
    val list: ArrayList<RetailNotifications>,
    @SerializedName("totalCount")
    val totalCount: Int? = null
)