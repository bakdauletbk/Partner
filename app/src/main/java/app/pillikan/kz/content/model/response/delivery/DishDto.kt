package app.pillikan.kz.content.model.response.delivery

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DishDto(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("img")
    val img: String? = null,
    @SerializedName("composition")
    val composition: String? = null,
    @SerializedName("status")
    val status: Int? = null,
    @SerializedName("price")
    val price: Double? = null
): Serializable