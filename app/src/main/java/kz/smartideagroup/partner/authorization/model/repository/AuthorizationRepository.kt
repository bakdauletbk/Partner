package kz.smartideagroup.partner.authorization.model.repository

import android.app.Application
import android.content.Context
import kz.smartideagroup.partner.BuildConfig
import kz.smartideagroup.partner.authorization.model.request.LoginBodyRequest
import kz.smartideagroup.partner.authorization.model.response.RetailInfoResponse
import kz.smartideagroup.partner.common.helpers.NetworkHelpers
import kz.smartideagroup.partner.common.preference.SessionManager
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.common.remote.Networking

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