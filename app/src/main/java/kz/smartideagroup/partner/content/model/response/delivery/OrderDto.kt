package kz.smartideagroup.partner.content.model.response.delivery

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OrderDto(
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("contactless")
    val contactless: Int? = null,
    @SerializedName("cookingDeadline")
    val cookingDeadline: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("deliveryAmount")
    val deliveryAmount: Double? = null,
    @SerializedName("foodAmount")
    val foodAmount: Double? = null,
    @SerializedName("fullAmount")
    val fullAmount: Double? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("latitude")
    val latitude: Double? = null,
    @SerializedName("longitude")
    val longitude: Double? = null,
    @SerializedName("retailId")
    val retailId: Long? = null,
    @SerializedName("retailLogo")
    val retailLogo: String? = null,
    @SerializedName("retailName")
    val retailName: String? = null,
    @SerializedName("status")
    var status: Int? = null,
    @SerializedName("type")
    val type: Int? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("utensils")
    val utensils: Int? = null,
    @SerializedName("orderItems")
    val orderItems: ArrayList<OrderItems>
) : Serializable