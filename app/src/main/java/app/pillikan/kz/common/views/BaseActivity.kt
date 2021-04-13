package app.pillikan.kz.common.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.pillikan.kz.R
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
}