package kz.smartideagroup.partner.content.viewmodel.accept_order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.smartideagroup.partner.content.model.repository.accept_order.ConfirmOrderRepository
import kz.smartideagroup.partner.content.model.request.delivery.ReqOrderStatus
import kz.smartideagroup.partner.content.model.response.delivery.OrderDto

class ConfirmOrderViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ConfirmOrderRepository = ConfirmOrderRepository(application)

    val isError = MutableLiveData<String>()
    val order = MutableLiveData<OrderDto>()
    val isOrderStatus = MutableLiveData<Boolean>()

    suspend fun getOrder(orderId: Int) {
        viewModelScope.launch {
            try {
                order.postValue(repository.getOrder(orderId))
            } catch (e: Exception) {
                isError.postValue(e.message)
            }
        }
    }

    suspend fun setOrderStatus(reqOrderStatus: ReqOrderStatus) {
        viewModelScope.launch {
            try {
                isOrderStatus.postValue(repository.setOrderStatus(reqOrderStatus))
            } catch (e: Exception) {
                isError.postValue(null)
            }
        }
    }

}