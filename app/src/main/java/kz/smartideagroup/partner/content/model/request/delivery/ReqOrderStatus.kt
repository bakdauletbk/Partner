package kz.smartideagroup.partner.content.model.request.delivery

import com.google.gson.annotations.SerializedName

data class ReqOrderStatus(
    @SerializedName("orderId")
    val orderId: Int? = null,
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("timeInMinutes")
    val timeInMinutes: Int? = null
)