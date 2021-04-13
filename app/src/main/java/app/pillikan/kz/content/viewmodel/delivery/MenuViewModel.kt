package app.pillikan.kz.content.viewmodel.delivery

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import app.pillikan.kz.content.model.repository.delivery.MenuRepository
import app.pillikan.kz.content.model.request.delivery.DishStatusRequest
import app.pillikan.kz.content.model.response.delivery.CategoriesItems

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MenuRepository(application)

    var isError = MutableLiveData<String>()
    var foodList = MutableLiveData<List<CategoriesItems>>()
    var isSendStatus = MutableLiveData<Boolean>()

    suspend fun getFoods() {
        viewModelScope.launch {
            try {
                foodList.postValue(repository.getFoods())
            } catch (e: Exception) {
                isError.postValue(e.message)
            }
        }
    }

    suspend fun setDishStatus(dishStatusRequest: DishStatusRequest) {
        viewModelScope.launch {
            try {
                isSendStatus.postValue(repository.setDishStatus(dishStatusRequest))
            } catch (e: Exception) {
                isError.postValue(e.message)
            }
        }
    }

}