package kz.smartideagroup.partner.content.model.response.reports

import com.google.gson.annotations.SerializedName

data class HistoryResponse (
    @SerializedName("payments")
    val payments : Payments?
)