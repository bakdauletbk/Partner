package kz.smartideagroup.partner.content.model.response.reports

import com.google.gson.annotations.SerializedName

data class ReportsItems(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("type")
    val type: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("orderId")
    val orderId: Long? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("amount")
    val amount: Double? = null,
    @SerializedName("realAmount")
    val realAmount: Double? = null,
    @SerializedName("commissionAmount")
    val commissionAmount: Double? = null
)