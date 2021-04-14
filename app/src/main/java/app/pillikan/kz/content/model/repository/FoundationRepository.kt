package app.pillikan.kz.content.model.repository

import android.app.Application
import android.content.Context
import app.pillikan.kz.BuildConfig
import app.pillikan.kz.common.preference.SessionManager
import app.pillikan.kz.common.remote.Constants
import app.pillikan.kz.common.remote.Networking
import app.pillikan.kz.content.model.request.home.SaveFirebaseTokenRequest
import okhttp3.ResponseBody
import retrofit2.Response

class FoundationRepository(application: Application) {

    private val networkService =
        Networking.create(Constants.BASE_URL)
    private var sharedPreferences =
        application.getSharedPreferences("sessionManager", Context.MODE_PRIVATE)
    private var sessionManager: SessionManager = SessionManager(sharedPreferences)

    fun getIsAuthorize(): Boolean = sessionManager.getIsAuthorize()!!

    suspend fun sendFireBaseToken(saveFireBase: SaveFirebaseTokenRequest): Response<ResponseBody> {
        return networkService.sendFirebaseToken(
            token = sessionManager.getToken().toString(),
            appver = BuildConfig.VERSION_NAME,
            contentType = Constants.CONTENT_TYPE,
            clientId = Constants.CLIENT_ID,
            saveFirebaseTokenRequest = saveFireBase
        )
    }
}