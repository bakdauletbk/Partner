package kz.smartideagroup.partner

import android.app.Application
import kz.smartideagroup.partner.common.remote.Constants
import java.util.*

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDate()
    }

    private fun initDate() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, +Constants.ONE)
        Constants.dayTo = calendar[Calendar.DAY_OF_MONTH]
        Constants.monthTo = calendar[Calendar.MONTH].plus(Constants.ONE)
        Constants.yearTo = calendar[Calendar.YEAR]
        val calendarFrom = Calendar.getInstance()
        calendarFrom.add(Calendar.MONTH, -Constants.ONE)
        Constants.dayFrom = calendarFrom[Calendar.DAY_OF_MONTH]
        Constants.monthFrom = calendarFrom[Calendar.MONTH] + Constants.ONE
        Constants.yearFrom = calendarFrom[Calendar.YEAR]
    }
}