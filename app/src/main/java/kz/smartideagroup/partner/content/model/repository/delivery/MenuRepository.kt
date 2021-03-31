package kz.smartideagroup.partner.content.model.repository.delivery

import android.app.Application
import android.content.Context
import kz.smartideagroup.partner.BuildConfig
import kz.smartideagroup.partner.common.preference.SessionManager
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.common.remote.Networking
import kz.smartideagroup.partner.content.model.request.delivery.DishStatusRequest
import kz.smartideagroup.partner.content.model.response.delivery.CategoriesItems
import kz.smartideagroup.partner.content.model.response.delivery.DishDto

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
        return when (response.code() == Constants.RESPONSE_SUCCESS_CODE) {
            true -> response.body()?.categories
            false -> null
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