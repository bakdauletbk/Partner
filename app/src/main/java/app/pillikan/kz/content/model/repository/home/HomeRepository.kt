package app.pillikan.kz.content.model.repository.home

import android.app.Application
import android.content.Context
import app.pillikan.kz.BuildConfig
import app.pillikan.kz.common.helpers.NetworkHelpers
import app.pillikan.kz.common.preference.SessionManager
import app.pillikan.kz.common.remote.Constants
import app.pillikan.kz.common.remote.Networking
import app.pillikan.kz.content.model.request.home.SaveFirebaseTokenRequest

class HomeRepository(application: Application) {

    private val networkService =
        Networking.create(Constants.BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("sessionManager", Context.MODE_PRIVATE)
    private var sessionManager: SessionManager = SessionManager(sharedPreferences)

    fun getRetailName(): String = sessionManager.getRetailName()!!

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