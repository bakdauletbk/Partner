package kz.smartideagroup.partner.content.model.repository.notification

import android.app.Application
import android.content.Context
import kz.smartideagroup.partner.BuildConfig
import kz.smartideagroup.partner.common.models.Page
import kz.smartideagroup.partner.common.preference.SessionManager
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.common.remote.Networking
import kz.smartideagroup.partner.content.model.request.notification.NotificationPageRequest
import kz.smartideagroup.partner.content.model.response.notification.RetailNotifications

class NotificationRepository(application: Application) {

    private val networkService =
        Networking.create(Constants.BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("sessionManager", Context.MODE_PRIVATE)
    private var sessionManager: SessionManager =
        SessionManager(sharedPreferences)

    suspend fun getNotifications(page: Int): Page<RetailNotifications>? {
        val notificationPageRequest = NotificationPageRequest(page)
        val response = networkService.setNotifications(
            sessionManager.getToken().toString(),
            BuildConfig.VERSION_NAME,
            notificationPageRequest
        )
        return if (response.code() == Constants.RESPONSE_SUCCESS_CODE) {
            val notifications: ArrayList<RetailNotifications> = ArrayList()
            val totalCount = response.body()?.totalCount
            for (i in response.body()?.list?.indices!!) {
                notifications.add(response.body()!!.list[i])
            }

            val notificationPage: Page<RetailNotifications>? =
                Page(notifications, totalCount!!)

            notificationPage
        } else {
            null
        }
    }

}