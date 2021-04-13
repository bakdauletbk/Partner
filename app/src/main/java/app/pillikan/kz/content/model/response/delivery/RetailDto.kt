package app.pillikan.kz.content.model.response.delivery

import com.google.gson.annotations.SerializedName
import app.pillikan.kz.content.model.response.home.WorkDay

data class RetailDto(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("logo")
    val logo: String? = null,
    @SerializedName("latitude")
    val latitude: String? = null,
    @SerializedName("longitude")
    val longitude: String? = null,
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("dlvCashBack")
    val dlvCashBack: Int? = null,
    @SerializedName("cashBack")
    val cashBack: Int? = null,
    @SerializedName("pillikanDelivery")
    val pillikanDelivery: Int? = null,
    @SerializedName("workDays")
    val workDays: ArrayList<WorkDay>,
    @SerializedName("balance")
    val balance: Double? = null

)