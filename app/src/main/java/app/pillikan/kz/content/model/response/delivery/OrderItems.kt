package app.pillikan.kz.content.model.response.delivery

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OrderItems(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("dish")
    val dish: DishDto? = null,
    @SerializedName("amount")
    val amount: Double? = null,
    @SerializedName("quantity")
    val quantity: Int? = null
): Serializable