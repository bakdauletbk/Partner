package kz.smartideagroup.partner.content.model.repository.home

import android.app.Application
import android.content.Context
import android.util.Log
import kz.smartideagroup.partner.BuildConfig
import kz.smartideagroup.partner.common.helpers.NetworkHelpers
import kz.smartideagroup.partner.common.preference.SessionManager
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.common.remote.Networking
import kz.smartideagroup.partner.content.model.request.home.SaveFirebaseTokenRequest

class HomeRepository(application: Application) {

    private val networkService =
        Networking.create(Constants.BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("sessionManager", Context.MODE_PRIVATE)
    private var sessionManager: SessionManager = SessionManager(sharedPreferences)

    fun getRetailName(): String = sessionManager.getRetailName()!!

    fun getAuthorize(): Boolean = sessionManager.getIsAuthorize()!!

    suspend fun checkNetwork(context: Context): Boolean {
        return NetworkHelpers.isNetworkConection(context)
    }

    suspend fun sendFireBaseToken(token: String): Boolean {
        val saveFirebaseTokenBody = SaveFirebaseTokenRequest(token)
        val response =
            networkService.sendFirebaseToken(token = sessionManager.getToken().toString(),
            appver = BuildConfig.VERSION_NAME,
            contentType = Constants.CONTENT_TYPE,
            clientId = Constants.CLIENT_ID,
            saveFirebaseTokenRequest = saveFirebaseTokenBody)
        return response.code() == Constants.RESPONSE_SUCCESS_CODE
    }
}