package app.pillikan.kz.content.model.repository.reports

import android.app.Application
import android.content.Context
import app.pillikan.kz.BuildConfig
import app.pillikan.kz.common.models.PageOrder
import app.pillikan.kz.common.preference.SessionManager
import app.pillikan.kz.common.remote.Constants
import app.pillikan.kz.common.remote.Networking
import app.pillikan.kz.content.model.response.reports.ReportsItems

class DeliveryReportsRepository(application: Application) {

    private val networkService =
        Networking.create(Constants.BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("sessionManager", Context.MODE_PRIVATE)
    private var sessionManager: SessionManager =
        SessionManager(sharedPreferences)

    suspend fun getReports(page: Int): PageOrder<ReportsItems>? {

        val response = networkService.getHistory(
            token = sessionManager.getToken().toString(),
            appver = BuildConfig.VERSION_NAME,
            endDate = Constants.yearTo.toString() + "-" + (Constants.monthTo).toString() + "-" + Constants.dayTo.toString(),
            startDate = Constants.yearFrom.toString() + "-" + (Constants.monthFrom).toString() + "-" + Constants.dayFrom.toString(),
            type = Constants.ONE,
            page = page,
            size = Constants.PAGE_LIMIT
        )

        return if (response.code() == Constants.RESPONSE_SUCCESS_CODE) {
            val hasNextPage = !response.body()?.payments?.hasNext!!
            val reportList: ArrayList<ReportsItems> = ArrayList()
            for (i in response.body()?.payments?.content?.indices!!) {
                reportList.add(response.body()?.payments?.content!![i])
            }
            val notificationPage: PageOrder<ReportsItems>? =
                PageOrder(reportList, hasNextPage)

            notificationPage
        } else {
            null
        }

    }
}