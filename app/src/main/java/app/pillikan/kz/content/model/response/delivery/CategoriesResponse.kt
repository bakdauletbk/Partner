package app.pillikan.kz.content.model.response.delivery

import com.google.gson.annotations.SerializedName

data class CategoriesResponse (
    @SerializedName("categories")
    val categories : ArrayList<CategoriesItems>
)