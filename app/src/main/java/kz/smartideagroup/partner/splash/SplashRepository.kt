package kz.smartideagroup.partner.splash

import android.app.Application
import android.content.Context
import kz.smartideagroup.partner.common.helpers.NetworkHelpers
import kz.smartideagroup.partner.common.preference.SessionManager

class SplashRepository (application: Application){

    private var sharedPreferences =
        application.getSharedPreferences("sessionManager", Context.MODE_PRIVATE)
    private var sessionManager: SessionManager = SessionManager(sharedPreferences)

    fun checkNetwork(context: Context): Boolean {
        return NetworkHelpers.isNetworkConection(context)
    }

    fun checkAuthorize(): Boolean? {
        return sessionManager.getIsAuthorize()
    }

}