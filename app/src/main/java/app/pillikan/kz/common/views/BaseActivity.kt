package app.pillikan.kz.common.views

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.pillikan.kz.R
import app.pillikan.kz.common.remote.Constants
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import org.jetbrains.anko.alert

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun errorDialog(errorMsg: String) {
        alert {
            title = getString(R.string.error_unknown_title)
            message = errorMsg
            isCancelable = false
            positiveButton(getString(R.string.dialog_ok)) { dialog ->
                dialog.dismiss()
            }
        }.show()
    }

    fun showAlertDialog(
        context: Context,
        message: String,
        isUpdate: Boolean
    ) {
        val builder = MaterialDialog.Builder(context)
            .title(message)
            .dividerColor(context.resources.getColor(R.color.blue))
            .positiveText("OK")
            .positiveColorRes(R.color.blue)
        if (isUpdate) {
            builder.cancelable(false)
            builder.onAny { dialog: MaterialDialog?, which: DialogAction ->
                if (which == DialogAction.POSITIVE) {
                    val appPackageName =
                        context.packageName
                    try {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(Constants.URI_PLAY_MARKET + appPackageName)
                            )
                        )
                        (context as Activity).finish()
                    } catch (e: ActivityNotFoundException) {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(Constants.URI_APP + appPackageName)
                            )
                        )
                        (context as Activity).finish()
                    }
                }
            }
        }
        builder.show()
    }


}