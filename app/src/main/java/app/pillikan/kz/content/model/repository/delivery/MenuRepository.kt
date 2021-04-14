package app.pillikan.kz.content.model.repository.delivery

import android.app.Application
import android.content.Context
import app.pillikan.kz.BuildConfig
import app.pillikan.kz.common.preference.SessionManager
import app.pillikan.kz.common.remote.Constants
import app.pillikan.kz.common.remote.Networking
import app.pillikan.kz.content.model.request.delivery.DishStatusRequest
import app.pillikan.kz.content.model.response.delivery.CategoriesItems

class MenuRepository(application: Application) {

    private val networkService =
        Networking.create(Constants.BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("sessionManager", Context.MODE_PRIVATE)
    private var sessionManager: SessionManager =
        SessionManager(sharedPreferences)

    suspend fun getFoods(): List<CategoriesItems>? {
        val response = networkService.getCategories(
            sessionManager.getToken().toString()
        )

        return if (response.code() == Constants.RESPONSE_SUCCESS_CODE) {
            response.body()?.categories
        } else {
            null
        }
    }

    suspend fun setDishStatus(dishStatusRequest: DishStatusRequest): Boolean {
        val response = networkService.setDishesStatus(
            token = sessionManager.getToken().toString(),
            appver = BuildConfig.VERSION_NAME,
            dishStatusRequest = dishStatusRequest
        )
        return response.code() == Constants.RESPONSE_SUCCESS_CODE
    }
}