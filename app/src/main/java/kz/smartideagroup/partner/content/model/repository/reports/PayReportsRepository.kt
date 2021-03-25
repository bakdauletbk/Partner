package kz.smartideagroup.partner.content.model.repository.reports

import android.app.Application
import android.content.Context
import kz.smartideagroup.partner.BuildConfig
import kz.smartideagroup.partner.common.models.PageOrder
import kz.smartideagroup.partner.common.preference.SessionManager
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.common.remote.Networking
import kz.smartideagroup.partner.content.model.response.reports.ReportsItems

class PayReportsRepository(application: Application) {

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
            type = Constants.ZERO,
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