package app.pillikan.kz.authorization.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import app.pillikan.kz.authorization.model.repository.AuthorizationRepository
import app.pillikan.kz.authorization.model.request.LoginBodyRequest
import app.pillikan.kz.common.remote.Constants

class AuthorizationViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: AuthorizationRepository = AuthorizationRepository(application)

    val isError: MutableLiveData<String> = MutableLiveData()
    val isNetworkConnection: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val isUpdateApp: MutableLiveData<Boolean> = MutableLiveData()

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
                when (response.code()) {
                    Constants.RESPONSE_SUCCESS_CODE -> {
                        isSuccess.postValue(true)
                        repository.saveUser(response.body()!!)
                    }
                    Constants.UPDATE_APP -> isUpdateApp.postValue(true)
                    else -> isSuccess.postValue(false)
                }

            } catch (e: Exception) {
                isError.postValue(null)
            }
        }
    }

}