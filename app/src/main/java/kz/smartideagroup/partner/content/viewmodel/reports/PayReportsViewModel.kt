package kz.smartideagroup.partner.content.viewmodel.reports

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.smartideagroup.partner.common.models.PageOrder
import kz.smartideagroup.partner.content.model.repository.reports.AllReportsRepository
import kz.smartideagroup.partner.content.model.repository.reports.PayReportsRepository
import kz.smartideagroup.partner.content.model.response.reports.ReportsItems

class PayReportsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PayReportsRepository(application)

    val isError = MutableLiveData<String>()
    val reportList = MutableLiveData<List<ReportsItems>>()

    private var reportPage: PageOrder<ReportsItems>? = null
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
        return reportPage != null && reportPage!!.hasNextPage()
    }

    fun isLoading(): Boolean {
        return isLoading
    }

    private fun loadPage(page: Int) {
        viewModelScope.launch {
            try {
                isLoading = true
                val response = repository.getReports(page)
                if (response != null) {
                    isLoading = false
                    reportPage = response
                    if (reportPage!!.getContent()!!.isNullOrEmpty()) {
                        reportList.postValue(null)
                    } else {
                        reportList.postValue(reportPage!!.getContent())
                    }
                }else{
                    isLoading = false
                }
            } catch (e: Exception) {
                isError.postValue(e.message)
            }
        }
    }

}