package app.pillikan.kz.content.model.repository.delivery

import android.app.Application
import android.content.Context
import app.pillikan.kz.BuildConfig
import app.pillikan.kz.common.models.PageOrder
import app.pillikan.kz.common.preference.SessionManager
import app.pillikan.kz.common.remote.Constants
import app.pillikan.kz.common.remote.Networking
import app.pillikan.kz.content.model.request.delivery.DeliveryStatusRequest
import app.pillikan.kz.content.model.request.delivery.ReqOrderStatus
import app.pillikan.kz.content.model.response.delivery.OrderDto
import app.pillikan.kz.content.model.response.delivery.RetailDto

class DeliveryRepository(application: Application) {

    private val networkService =
        Networking.create(Constants.BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("sessionManager", Context.MODE_PRIVATE)
    private var sessionManager: SessionManager =
        SessionManager(sharedPreferences)

    suspend fun getOrderActive(): List<OrderDto>? {
        val response = networkService.getOrderActive(
            sessionManager.getToken().toString()
        )
        return if (response.code() == Constants.RESPONSE_SUCCESS_CODE) {
            response.body()?.orders
        } else {
            null
        }
    }

    suspend fun setDeliveryStatus(deliveryStatusRequest: DeliveryStatusRequest): Boolean {
        val response = networkService.setDeliveryStatus(
            sessionManager.getToken().toString(),
            BuildConfig.VERSION_NAME,
            deliveryStatusRequest
        )
        return response.code() == Constants.RESPONSE_SUCCESS_CODE
    }

    suspend fun setOrderStatus(reqOrderStatus: ReqOrderStatus): Boolean {
        val response = networkService.setOrderStatus(
            sessionManager.getToken().toString(),
            BuildConfig.VERSION_NAME,
            reqOrderStatus
        )
        return response.code() == Constants.RESPONSE_SUCCESS_CODE
    }

    suspend fun getOrderCompleted(page: Int): PageOrder<OrderDto>? {
        val pageLimit = Constants.PAGE_LIMIT
        val response = networkService.getOrderCompleted(
            token = sessionManager.getToken().toString(),
            appver = BuildConfig.VERSION_NAME,
            page = page,
            size = pageLimit
        )
        return if (response.code() == Constants.RESPONSE_SUCCESS_CODE) {
            val hasNextPage = response.body()?.orders?.hasNext!!
            val orderList: ArrayList<OrderDto> = ArrayList()
            for (i in response.body()?.orders?.content!!.indices) {
                orderList.add(response.body()!!.orders?.content!![i])
            }

            val orderPage: PageOrder<OrderDto>? =
                PageOrder(content = orderList,hasNextPage = hasNextPage)

            orderPage
        } else {
            null
        }
    }

    suspend fun getRetailInfo(): RetailDto? {
        val response = networkService.getRetailStatus(
            sessionManager.getToken().toString()
        )
        return if (response.code() == Constants.RESPONSE_SUCCESS_CODE) {
            response.body()?.retail
        } else {
            null
        }
    }
}