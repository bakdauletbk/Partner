package kz.smartideagroup.partner.content.viewmodel.delivery

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.smartideagroup.partner.common.models.PageOrder
import kz.smartideagroup.partner.content.model.repository.delivery.DeliveryRepository
import kz.smartideagroup.partner.content.model.request.delivery.DeliveryStatusRequest
import kz.smartideagroup.partner.content.model.request.delivery.ReqOrderStatus
import kz.smartideagroup.partner.content.model.response.delivery.OrderDto
import kz.smartideagroup.partner.content.model.response.delivery.RetailDto

class DeliveryViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: DeliveryRepository = DeliveryRepository(application)

    var isError = MutableLiveData<String>()
    var orderActive = MutableLiveData<List<OrderDto>>()
    var retailInfo = MutableLiveData<RetailDto>()
    var isDeliveryStatus = MutableLiveData<Boolean>()
    var isOrderStatus = MutableLiveData<Boolean>()

    var orderCompletedList = MutableLiveData<List<OrderDto>>()
    private var orderCompletedPage: PageOrder<OrderDto>? = null
    private var isLoading = true

    fun getInitialPage() {
        loadPage(0)
    }

    fun getNextPage(nextPage:Int) {
        viewModelScope.launch {
            loadPage(nextPage)
        }
    }

    fun isHasNext(): Boolean {
        return orderCompletedPage != null && orderCompletedPage!!.hasNextPage()
    }

    fun isLoading(): Boolean {
        return isLoading
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
                isLoading = true
                val response = repository.getOrderCompleted(page)
                if (response != null) {
                    isLoading = false
                    orderCompletedPage = response
                    if (orderCompletedPage!!.getContent()!!.isNullOrEmpty()) {
                        orderCompletedList.postValue(null)
                    } else {
                        orderCompletedList.postValue(orderCompletedPage!!.getContent())
                    }
                }else{
                    isLoading = false
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