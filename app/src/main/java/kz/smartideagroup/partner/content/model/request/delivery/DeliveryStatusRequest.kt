package kz.smartideagroup.partner.content.model.request.delivery

import com.google.gson.annotations.SerializedName

data class DeliveryStatusRequest(
    @SerializedName("status")
    val status: Int? = null
)