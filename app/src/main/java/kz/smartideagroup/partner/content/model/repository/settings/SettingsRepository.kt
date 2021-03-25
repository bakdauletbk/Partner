package kz.smartideagroup.partner.content.model.repository.settings

import android.app.Application
import android.content.Context
import kz.smartideagroup.partner.common.preference.SessionManager

class SettingsRepository(application: Application) {

    private var sharedPreferences =
        application.getSharedPreferences("sessionManager", Context.MODE_PRIVATE)
    private var sessionManager: SessionManager =
        SessionManager(sharedPreferences)

    fun getRetailName(): String = sessionManager.getRetailName()!!

    fun logout(): Boolean {
        return try {
            sessionManager.clear()
            true
        } catch (e: Exception) {
            false
        }
    }
}