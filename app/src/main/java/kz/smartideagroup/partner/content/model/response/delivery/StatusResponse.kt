package kz.smartideagroup.partner.content.model.response.delivery

import com.google.gson.annotations.SerializedName

data class StatusResponse (
    @SerializedName("orders")
    val orders : List<OrderDto>?
)