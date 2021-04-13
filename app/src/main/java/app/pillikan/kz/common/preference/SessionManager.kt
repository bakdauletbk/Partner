package app.pillikan.kz.common.preference

import android.content.SharedPreferences

class SessionManager(private val pref: SharedPreferences) {

    companion object {
        const val KEY_IS_AUTHORIZE = "KEY_IS_AUTHORIZE"
        const val KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN"
        const val RETAIL_NAME = "RETAIL_NAME"
        const val ID = "ID"
    }

    fun getIsAuthorize(): Boolean? = pref.getBoolean(KEY_IS_AUTHORIZE, false)
    fun setIsAuthorize(isAuthorize: Boolean) {
        pref.edit().putBoolean(KEY_IS_AUTHORIZE, isAuthorize).apply()
    }

    fun getToken(): String? = pref.getString(KEY_ACCESS_TOKEN, null)
    fun setToken(token: String) {
        pref.edit().putString(KEY_ACCESS_TOKEN, token).apply()
    }

    fun setRetailName(username: String?) {
        pref.edit().putString(RETAIL_NAME, username).apply()
    }

    fun getRetailName(): String? = pref.getString(RETAIL_NAME, "")

    fun setId(id: Int?) {
        pref.edit().putInt(ID, id!!).apply()
    }

    fun getId(): Int? = pref.getInt(ID, 0)

    fun clear() {
        pref.edit().clear().apply()
    }

}