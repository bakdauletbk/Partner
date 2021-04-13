package app.pillikan.kz.content.model.response.delivery

import com.google.gson.annotations.SerializedName

data class StatusResponse (
    @SerializedName("orders")
    val orders : List<OrderDto>?
)