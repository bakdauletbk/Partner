package kz.smartideagroup.partner.content.model.response.delivery

import com.google.gson.annotations.SerializedName

data class DishResponse (
    @SerializedName("dish")
    val dish:DishDto
)