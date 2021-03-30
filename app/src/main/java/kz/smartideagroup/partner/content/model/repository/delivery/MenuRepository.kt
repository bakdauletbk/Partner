package kz.smartideagroup.partner.content.model.repository.delivery

import android.app.Application
import android.content.Context
import kz.smartideagroup.partner.common.preference.SessionManager
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.common.remote.Networking
import kz.smartideagroup.partner.content.model.response.delivery.CategoriesItems

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

}