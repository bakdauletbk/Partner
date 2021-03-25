package kz.smartideagroup.partner.content.model.response.delivery

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("order")
    val order: OrderDto
)