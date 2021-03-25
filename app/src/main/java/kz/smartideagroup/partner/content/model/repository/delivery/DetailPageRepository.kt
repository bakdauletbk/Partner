package kz.smartideagroup.partner.content.model.repository.delivery

import android.app.Application
import android.content.Context
import kz.smartideagroup.partner.BuildConfig
import kz.smartideagroup.partner.common.preference.SessionManager
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.common.remote.Networking
import kz.smartideagroup.partner.content.model.request.delivery.ReqOrderStatus

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