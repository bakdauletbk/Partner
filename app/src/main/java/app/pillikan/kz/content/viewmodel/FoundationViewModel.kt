package app.pillikan.kz.content.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.pillikan.kz.common.remote.Constants
import kotlinx.coroutines.launch
import app.pillikan.kz.content.model.repository.FoundationRepository
import app.pillikan.kz.content.model.request.home.SaveFirebaseTokenRequest

class FoundationViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "FoundationViewModel"
    }

    private val repository = FoundationRepository(application)

    val isAuthorize = MutableLiveData<Boolean>()
    val isSendFToken = MutableLiveData<Boolean>()
    val isUpdateApp = MutableLiveData<Boolean>()

    fun getIsAuthorize() {
        try {
            isAuthorize.postValue(repository.getIsAuthorize())
        } catch (e: NullPointerException) {
        }
    }

    suspend fun sendFireBaseToken(saveFirebaseTokenRequest: SaveFirebaseTokenRequest) {
        viewModelScope.launch {
            try {
                val response = repository.sendFireBaseToken(saveFirebaseTokenRequest)
                when (response.code()) {
                    Constants.RESPONSE_SUCCESS_CODE -> isSendFToken.postValue(true)
                    Constants.UPDATE_APP -> isUpdateApp.postValue(true)
                    else -> isSendFToken.postValue(false)
                }
            } catch (e: Exception) {
            }
        }
    }

}