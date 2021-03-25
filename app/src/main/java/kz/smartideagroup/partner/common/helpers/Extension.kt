package kz.smartideagroup.partner.common.helpers

import android.annotation.SuppressLint
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
fun convertTimes(time: String): Calendar? {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    parser.timeZone = TimeZone.getTimeZone("GMT+6")
    parser.isLenient = false
    var parse: Date? = null
    try {
        parse = parser.parse(time)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    val calendar = Calendar.getInstance()
    calendar.time = parse
    return calendar
}
