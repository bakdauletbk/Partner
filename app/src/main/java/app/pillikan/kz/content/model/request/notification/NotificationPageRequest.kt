package app.pillikan.kz.content.model.request.notification

import com.google.gson.annotations.SerializedName

data class NotificationPageRequest(
    @SerializedName("pageNumber")
    val pageNumber: Int? = null
)