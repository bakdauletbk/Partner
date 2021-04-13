package app.pillikan.kz.content.model.response.reports

import com.google.gson.annotations.SerializedName

data class Payments(
    @SerializedName("hasPrevious")
    val hasPrevious: Boolean = false,
    @SerializedName("hasNext")
    val hasNext: Boolean = false,
    @SerializedName("totalElements")
    val totalElements: Int? = null,
    @SerializedName("content")
    val content: ArrayList<ReportsItems>
)