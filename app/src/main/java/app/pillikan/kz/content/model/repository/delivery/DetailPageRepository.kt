package app.pillikan.kz.content.model.repository.delivery

import android.app.Application
import android.content.Context
import app.pillikan.kz.BuildConfig
import app.pillikan.kz.common.preference.SessionManager
import app.pillikan.kz.common.remote.Constants
import app.pillikan.kz.common.remote.Networking
import app.pillikan.kz.content.model.request.delivery.ReqOrderStatus

class DetailPageRepository(application: Application) {

    private val networkService =
        Networking.create(Constants.BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("sessionManager", Context.MODE_PRIVATE)
    private var sessionManager: SessionManager =
        SessionManager(sharedPreferences)

    suspend fun setOrderStatus(reqOrderStatus: ReqOrderStatus): Boolean {
        val response = networkService.setOrderStatus(
            sessionManager.getToken().toString(),
            BuildConfig.VERSION_NAME,
            reqOrderStatus
        )
        return response.code() == Constants.RESPONSE_SUCCESS_CODE
    }

}