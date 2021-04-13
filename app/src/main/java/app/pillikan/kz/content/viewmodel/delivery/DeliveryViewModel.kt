package app.pillikan.kz.content.viewmodel.delivery

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import app.pillikan.kz.common.models.PageOrder
import app.pillikan.kz.content.model.repository.delivery.DeliveryRepository
import app.pillikan.kz.content.model.request.delivery.DeliveryStatusRequest
import app.pillikan.kz.content.model.request.delivery.ReqOrderStatus
import app.pillikan.kz.content.model.response.delivery.OrderDto
import app.pillikan.kz.content.model.response.delivery.RetailDto

class DeliveryViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: DeliveryRepository = DeliveryRepository(application)

    var isError = MutableLiveData<String>()
    var orderActive = MutableLiveData<List<OrderDto>>()
    var retailInfo = MutableLiveData<RetailDto>()
    var isDeliveryStatus = MutableLiveData<Boolean>()
    var isOrderStatus = MutableLiveData<Boolean>()

    val isHasNextPage = MutableLiveData<Boolean>()
    var orderCompletedList = MutableLiveData<List<OrderDto>>()
    var orderCompletedPage: PageOrder<OrderDto>? = null


    fun getInitialPage() {
        loadPage(0)
    }

    fun getNextPage(nextPage: Int) {
        viewModelScope.launch {
            loadPage(nextPage)
        }
    }

    suspend fun getOrderActive() {
        viewModelScope.launch {
            try {
                orderActive.postValue(repository.getOrderActive())
            } catch (e: Exception) {
                isError.postValue(e.message.toString())
            }
        }
    }

    suspend fun setDeliveryStatus(deliveryStatusRequest: DeliveryStatusRequest) {
        viewModelScope.launch {
            try {
                isDeliveryStatus.postValue(repository.setDeliveryStatus(deliveryStatusRequest))
            } catch (e: Exception) {
                isError.postValue(e.message.toString())
            }
        }
    }

    private fun loadPage(page: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getOrderCompleted(page)
                orderCompletedPage = response
                isHasNextPage.postValue(orderCompletedPage!!.hasNextPage())
                if (orderCompletedPage!!.getContent()!!.isNullOrEmpty()) {
                    orderCompletedList.postValue(null)
                } else {
                    orderCompletedList.postValue(orderCompletedPage!!.getContent())
                }
            } catch (e: Exception) {
                isError.postValue(e.message.toString())
            }
        }
    }

    suspend fun setOrderStatus(reqOrderStatus: ReqOrderStatus) {
        viewModelScope.launch {
            try {
                isOrderStatus.postValue(repository.setOrderStatus(reqOrderStatus))
            } catch (e: Exception) {
                isError.postValue(e.message.toString())
            }
        }
    }

    suspend fun getRetailInfo() {
        viewModelScope.launch {
            try {
                retailInfo.postValue(repository.getRetailInfo())
            } catch (e: Exception) {
                isError.postValue(e.message.toString())
            }
        }
    }

}