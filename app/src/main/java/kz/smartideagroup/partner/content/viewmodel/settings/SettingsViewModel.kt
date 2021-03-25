package kz.smartideagroup.partner.content.viewmodel.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kz.smartideagroup.partner.content.model.repository.settings.SettingsRepository

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SettingsRepository = SettingsRepository(application)

    var retailName = MutableLiveData<String>()
    var logout = MutableLiveData<Boolean>()

    fun getRetailName() {
        retailName.postValue(repository.getRetailName())
    }

    fun logout() {
        logout.postValue(repository.logout())
    }

}