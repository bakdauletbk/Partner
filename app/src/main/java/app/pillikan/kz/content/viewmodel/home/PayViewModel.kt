package app.pillikan.kz.content.viewmodel.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import app.pillikan.kz.content.model.repository.home.PayRepository

class PayViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "PayViewModel"
    }

    private val repository = PayRepository(application)

    val orderId = MutableLiveData<String>()

    fun getOrderId() {
        try {
            orderId.postValue(repository.getOrderId())
        } catch (e: NullPointerException) {
        }
    }

}