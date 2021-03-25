package kz.smartideagroup.partner.content.viewmodel.delivery

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.smartideagroup.partner.content.model.repository.delivery.DetailPageRepository
import kz.smartideagroup.partner.content.model.request.delivery.ReqOrderStatus

class DetailPageViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DetailPageRepository = DetailPageRepository(application)

    val isError = MutableLiveData<String>()
    val isOrderStatus = MutableLiveData<Boolean>()

    suspend fun setOrderStatus(reqOrderStatus: ReqOrderStatus) {
        viewModelScope.launch {
            try {
                isOrderStatus.postValue(repository.setOrderStatus(reqOrderStatus))
            } catch (e: Exception) {
                isError.postValue(e.message.toString())
            }
        }
    }

}