package kz.smartideagroup.partner.content.model.repository.home

import android.app.Application
import android.content.Context
import android.util.Log
import kz.smartideagroup.partner.common.helpers.NetworkHelpers
import kz.smartideagroup.partner.common.preference.SessionManager

class HomeRepository(application: Application) {

    private var sharedPreferences =
        application.getSharedPreferences("sessionManager", Context.MODE_PRIVATE)
    private var sessionManager: SessionManager = SessionManager(sharedPreferences)

    fun getRetailName(): String = sessionManager.getRetailName()!!

    fun checkNetwork(context: Context): Boolean {
        return NetworkHelpers.isNetworkConection(context)
    }

}