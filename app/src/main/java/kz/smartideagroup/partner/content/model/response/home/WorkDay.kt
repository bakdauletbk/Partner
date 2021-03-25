package kz.smartideagroup.partner.content.model.response.home

import com.google.gson.annotations.SerializedName

data class WorkDay(
    @SerializedName("around")
    val around: Boolean? = false,
    @SerializedName("day")
    val day: Int? = null,
    @SerializedName("timeEnd")
    val timeEnd: String? = null,
    @SerializedName("timeStart")
    val timeStart: String? = null,
    @SerializedName("work")
    val work: Boolean? = false
)