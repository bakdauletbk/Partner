package app.pillikan.kz.content.viewmodel.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import app.pillikan.kz.content.model.repository.home.HomeRepository
import kotlin.Exception

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: HomeRepository = HomeRepository(application)

    val isNetworkConnection = MutableLiveData<Boolean>()
    val retailName = MutableLiveData<String>()
    val isSendFireBaseToken = MutableLiveData<Boolean>()
    val isError = MutableLiveData<String>()

    fun getRetailName() {
        retailName.postValue(repository.getRetailName())
    }

    suspend fun checkNetwork(context: Context) {
        viewModelScope.launch {
            try {
                isNetworkConnection.postValue(repository.checkNetwork(context))
            } catch (e: Exception) {
                isNetworkConnection.postValue(false)
            }
        }
    }

    suspend fun sendFireBaseToken(token: String) {
        viewModelScope.launch {
            try {
                isSendFireBaseToken.postValue(repository.sendFireBaseToken(token))
            } catch (e: Exception) {
                isError.postValue(e.message)
            }
        }
    }

}