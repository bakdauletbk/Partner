package kz.smartideagroup.partner.content.viewmodel.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.smartideagroup.partner.content.model.repository.home.HomeRepository
import java.lang.Exception

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: HomeRepository = HomeRepository(application)

    val isNetworkConnection = MutableLiveData<Boolean>()
    var retailName = MutableLiveData<String>()

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

}