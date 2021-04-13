package app.pillikan.kz.authorization.model.repository

import android.app.Application
import android.content.Context
import app.pillikan.kz.BuildConfig
import app.pillikan.kz.authorization.model.request.LoginBodyRequest
import app.pillikan.kz.authorization.model.response.RetailInfoResponse
import app.pillikan.kz.common.helpers.NetworkHelpers
import app.pillikan.kz.common.preference.SessionManager
import app.pillikan.kz.common.remote.Constants
import app.pillikan.kz.common.remote.Networking

class AuthorizationRepository(application: Application) {

    private val networkService =
        Networking.create(Constants.BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("sessionManager", Context.MODE_PRIVATE)
    private var sessionManager: SessionManager =
        SessionManager(sharedPreferences)

    fun checkNetwork(context: Context): Boolean {
        return NetworkHelpers.isNetworkConection(context)
    }

    suspend fun signIn(loginBodyRequest: LoginBodyRequest): Boolean {
        val response = networkService.signIn(
            appVer = BuildConfig.VERSION_NAME,
            clientId = Constants.CLIENT_ID,
            loginBodyRequest = loginBodyRequest
        )
        return if (response.code() == Constants.RESPONSE_SUCCESS_CODE) {
            saveUser(response.body()!!)
            true
        } else {
            sessionManager.clear()
            false
        }
    }

    private fun saveUser(body: RetailInfoResponse) {
        sessionManager.setRetailName(body.name)
        sessionManager.setToken("Bearer " + body.token!!.accessToken)
        sessionManager.setIsAuthorize(true)
        sessionManager.setId(body.identifier)
    }


}