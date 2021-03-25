package kz.smartideagroup.partner.authorization.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.smartideagroup.partner.authorization.model.repository.AuthorizationRepository
import kz.smartideagroup.partner.authorization.model.request.LoginBodyRequest

class AuthorizationViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: AuthorizationRepository = AuthorizationRepository(application)

    val isError: MutableLiveData<String> = MutableLiveData()
    val isNetworkConnection: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccess: MutableLiveData<Boolean> = MutableLiveData()


    suspend fun checkNetwork(context: Context) {
        viewModelScope.launch {
            try {
                isNetworkConnection.postValue(repository.checkNetwork(context))
            } catch (e: java.lang.Exception) {
                isNetworkConnection.postValue(false)
            }
        }
    }

    suspend fun signIn(loginBodyRequest: LoginBodyRequest) {
        viewModelScope.launch {
            try {
                val response = repository.signIn(loginBodyRequest)
                isSuccess.postValue(response)
            } catch (e: Exception) {
                isError.postValue(null)
            }
        }
    }

}