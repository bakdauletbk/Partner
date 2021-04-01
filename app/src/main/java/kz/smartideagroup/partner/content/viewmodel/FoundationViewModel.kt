package kz.smartideagroup.partner.content.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.smartideagroup.partner.content.model.repository.FoundationRepository
import kz.smartideagroup.partner.content.model.request.home.SaveFirebaseTokenRequest

class FoundationViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "FoundationViewModel"
    }

    private val repository = FoundationRepository(application)

    val isAuthorize = MutableLiveData<Boolean>()
    val isSendFToken = MutableLiveData<Boolean>()

    fun getIsAuthorize() {
        try {
            isAuthorize.postValue(repository.getIsAuthorize())
        } catch (e: NullPointerException) {
            Log.e(TAG, e.message.toString())
        }
    }

    suspend fun sendFireBaseToken(saveFirebaseTokenRequest: SaveFirebaseTokenRequest) {
        viewModelScope.launch {
            try {
                isSendFToken.postValue(repository.sendFireBaseToken(saveFirebaseTokenRequest))
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
            }
        }
    }

}