package app.pillikan.kz.content.viewmodel.notification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import app.pillikan.kz.common.models.Page
import app.pillikan.kz.content.model.repository.notification.NotificationRepository
import app.pillikan.kz.content.model.response.notification.RetailNotifications

class NotificationViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: NotificationRepository = NotificationRepository(application)

    val isError = MutableLiveData<String>()

    val notificationList = MutableLiveData<List<RetailNotifications>>()

    private var notificationsPage: Page<RetailNotifications>? = null
    private var isLoading = false

    fun getInitialPage() {
        loadPage(0)
    }

    fun getNextPage(nextPage:Int) {
        viewModelScope.launch {
            loadPage(nextPage)
        }
    }

    fun totalCount(): Int {
        return notificationsPage?.totalCount()!!
    }

    fun isLoading(): Boolean {
        return isLoading
    }

    private fun loadPage(page: Int) {
        viewModelScope.launch {
            try {
                isLoading = true
                val response = repository.getNotifications(page)
                if (response != null) {
                    isLoading = false
                    notificationsPage = response
                    if (notificationsPage!!.getContent()!!.isNullOrEmpty()) {
                        notificationList.postValue(null)
                    } else {
                        notificationList.postValue(notificationsPage!!.getContent())
                    }
                } else {
                    isLoading = false
                }
            } catch (e: Exception) {
                isError.postValue(null)
            }
        }
    }

}