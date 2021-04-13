package app.pillikan.kz.common.helpers

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

fun call(phone: String, context: Context, activity: Activity) {
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CALL_PHONE
        )
        != PackageManager.PERMISSION_GRANTED
    ) {

        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                Manifest.permission.CALL_PHONE
            )
        ) {
            Toast.makeText(
                context,
                "Чтобы позвонить, пожалуйста, Нажмите <Разрешить>",
                Toast.LENGTH_LONG
            ).show()
            ActivityCompat.requestPermissions(
                context,
                arrayOf(Manifest.permission.CALL_PHONE),
                42
            )
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CALL_PHONE
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:" + phone)
                activity.startActivity(intent)
            }

        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(
                context,
                arrayOf(Manifest.permission.CALL_PHONE),
                42
            )
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CALL_PHONE
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:" + phone)
                activity.startActivity(intent)
            }
        }
    } else {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:" + phone)
        activity.startActivity(intent)
        // Permission has already been granted
    }
}

fun openLink(url: String, activity: Activity) {
    try {
        val browserIntent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        activity.startActivity(browserIntent)
    } catch (ex: Exception) {
    }

}