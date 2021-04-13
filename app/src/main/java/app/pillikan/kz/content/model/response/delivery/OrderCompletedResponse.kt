package app.pillikan.kz.content.model.response.delivery

import com.google.gson.annotations.SerializedName

data class OrderCompletedResponse(
    @SerializedName("orders")
    val orders: OrderCompleted?
)