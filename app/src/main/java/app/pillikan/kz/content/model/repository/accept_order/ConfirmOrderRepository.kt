package app.pillikan.kz.content.model.repository.accept_order

import android.app.Application
import android.content.Context
import app.pillikan.kz.BuildConfig
import app.pillikan.kz.common.preference.SessionManager
import app.pillikan.kz.common.remote.Constants
import app.pillikan.kz.common.remote.Networking
import app.pillikan.kz.content.model.request.delivery.ReqOrderStatus
import app.pillikan.kz.content.model.response.delivery.OrderDto

class ConfirmOrderRepository(application: Application) {

    private val networkService =
        Networking.create(Constants.BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("sessionManager", Context.MODE_PRIVATE)
    private var sessionManager: SessionManager =
        SessionManager(sharedPreferences)

    suspend fun getOrder(orderId: Int): OrderDto? {
        val response = networkService.getOrderId(
            sessionManager.getToken().toString(),
            BuildConfig.VERSION_NAME,
            orderId
        )
        return if (response.code() == Constants.RESPONSE_SUCCESS_CODE) {
            response.body()?.order
        } else {
            null
        }
    }

    suspend fun setOrderStatus(reqOrderStatus: ReqOrderStatus): Boolean {
        val response = networkService.setOrderStatus(
            token = sessionManager.getToken().toString(),
            appver = BuildConfig.VERSION_NAME,
            orderStatus = reqOrderStatus
        )
        return response.code() == Constants.RESPONSE_SUCCESS_CODE
    }

}