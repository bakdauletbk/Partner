package app.pillikan.kz.content.model.response.delivery

import com.google.gson.annotations.SerializedName

data class OrderCompleted(
    @SerializedName("hasPrevious")
    val hasPrevious: Boolean = false,
    @SerializedName("hasNext")
    val hasNext: Boolean = false,
    @SerializedName("content")
    val content: ArrayList<OrderDto>?
)