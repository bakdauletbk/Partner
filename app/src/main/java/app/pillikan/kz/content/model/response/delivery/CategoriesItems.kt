package app.pillikan.kz.content.model.response.delivery

import com.google.gson.annotations.SerializedName

data class CategoriesItems(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("dishes")
    val dishes : ArrayList<DishDto>
)