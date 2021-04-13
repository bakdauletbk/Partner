package app.pillikan.kz.authorization.model.response

import com.google.gson.annotations.SerializedName

data class RetailInfoResponse(
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("balance")
    val balance: String? = null,
    @SerializedName("inBalance")
    val inBalance: String? = null,
    @SerializedName("cashBack")
    val cashBack: Int? = null,
    @SerializedName("qrCode")
    val qrCode: String? = null,
    @SerializedName("identifier")
    val identifier: Int? = null,
    @SerializedName("token")
    val token: AccessToken? = null
)