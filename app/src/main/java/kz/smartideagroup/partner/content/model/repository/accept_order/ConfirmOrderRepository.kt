package kz.smartideagroup.partner.content.model.repository.accept_order

import android.app.Application
import android.content.Context
import kz.smartideagroup.partner.BuildConfig
import kz.smartideagroup.partner.common.preference.SessionManager
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.common.remote.Networking
import kz.smartideagroup.partner.content.model.request.delivery.ReqOrderStatus
import kz.smartideagroup.partner.content.model.response.delivery.OrderDto

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