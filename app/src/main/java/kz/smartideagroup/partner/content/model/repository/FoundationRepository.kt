package kz.smartideagroup.partner.content.model.repository

import android.app.Application
import android.content.Context
import kz.smartideagroup.partner.BuildConfig
import kz.smartideagroup.partner.common.preference.SessionManager
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.common.remote.Networking
import kz.smartideagroup.partner.content.model.request.home.SaveFirebaseTokenRequest

class FoundationRepository(application: Application) {

    private val networkService =
        Networking.create(Constants.BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("sessionManager", Context.MODE_PRIVATE)
    private var sessionManager: SessionManager = SessionManager(sharedPreferences)

    fun getIsAuthorize(): Boolean = sessionManager.getIsAuthorize()!!

    suspend fun sendFireBaseToken(saveFireBase: SaveFirebaseTokenRequest): Boolean {
        val response = networkService.sendFirebaseToken(
            token = sessionManager.getToken().toString(),
            appver = BuildConfig.VERSION_NAME,
            contentType = Constants.CONTENT_TYPE,
            clientId = Constants.CLIENT_ID,
            saveFirebaseTokenRequest = saveFireBase
        )
        return response.code() == Constants.RESPONSE_SUCCESS_CODE
    }
}