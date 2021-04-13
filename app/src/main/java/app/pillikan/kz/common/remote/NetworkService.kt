package app.pillikan.kz.common.remote

import app.pillikan.kz.authorization.model.request.LoginBodyRequest
import app.pillikan.kz.authorization.model.response.RetailInfoResponse
import app.pillikan.kz.content.model.request.delivery.DishStatusRequest
import app.pillikan.kz.content.model.request.delivery.ReqOrderStatus
import app.pillikan.kz.content.model.request.delivery.DeliveryStatusRequest
import app.pillikan.kz.content.model.request.home.SaveFirebaseTokenRequest
import app.pillikan.kz.content.model.request.notification.NotificationPageRequest
import app.pillikan.kz.content.model.response.delivery.*
import app.pillikan.kz.content.model.response.notification.NotificationResponse
import app.pillikan.kz.content.model.response.reports.HistoryResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface NetworkService {

    @POST(EndPoints.POST_SIGN_IN)
    suspend fun signIn(
        @Header("appVer") appVer: String,
        @Header("clientId") clientId: String,
        @Body loginBodyRequest: LoginBodyRequest
    ): Response<RetailInfoResponse>

    @GET(EndPoints.GET_HISTORY)
    suspend fun getHistory(
        @Header("authorization") token: String,
        @Header("appver") appver: String,
        @Query("endDate") endDate: String,
        @Query("startDate") startDate: String,
        @Query("type") type: Int?,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<HistoryResponse>

    @GET(EndPoints.GET_ORDER_ACTIVE)
    suspend fun getOrderActive(
        @Header("authorization") token: String
    ): Response<StatusResponse?>

    @GET(EndPoints.GET_ORDER_COMPLETED)
    suspend fun getOrderCompleted(
        @Header("authorization") token: String,
        @Header("appver") appver: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<OrderCompletedResponse>

    @GET(EndPoints.GET_ORDER_ID)
    suspend fun getOrderId(
        @Header("authorization") token: String,
        @Header("appver") appver: String,
        @Query("id") id: Int
    ): Response<OrderResponse>

    @POST(EndPoints.POST_STATUS)
    suspend fun setOrderStatus(
        @Header("authorization") token: String,
        @Header("appver") appver: String,
        @Body orderStatus: ReqOrderStatus
    ): Response<StatusResponse>

    @GET(EndPoints.GET_CATEGORIES)
    suspend fun getCategories(
        @Header("authorization") token: String
    ): Response<CategoriesResponse>

    @POST(EndPoints.POST_STATUS_RETAIL)
    suspend fun setDeliveryStatus(
        @Header("authorization") token: String,
        @Header("appver") appver: String,
        @Body deliveryStatusRequest: DeliveryStatusRequest
    ): Response<ResponseBody>

    @GET(EndPoints.GET_STATUS_RETAIL)
    suspend fun getRetailStatus(
        @Header("authorization") token: String
    ): Response<RetailResponse>

    @POST(EndPoints.POST_DISHES_STATUS)
    suspend fun setDishesStatus(
        @Header("authorization") token: String,
        @Header("appver") appver: String,
        @Body dishStatusRequest: DishStatusRequest
    ): Response<DishResponse>

    @POST(EndPoints.POST_NOTIFICATION)
    suspend fun setNotifications(
        @Header("authorization") token: String,
        @Header("appver") appver: String,
        @Body notificationPageRequest: NotificationPageRequest
    ):Response<NotificationResponse>

    @POST(EndPoints.POST_FIREBASE_TOKEN)
    suspend fun sendFirebaseToken(
        @Header("authorization") token: String,
        @Header("appver") appver: String,
        @Header("Content-type") contentType: String,
        @Header("clientId") clientId :String,
        @Body saveFirebaseTokenRequest: SaveFirebaseTokenRequest
    ):Response<ResponseBody>

}