package app.pillikan.kz.content.model.request.delivery

import com.google.gson.annotations.SerializedName

data class DishStatusRequest(
    @SerializedName("dishId")
    val dishId: Long? = null,
    @SerializedName("status")
    val status: Int? = null
)