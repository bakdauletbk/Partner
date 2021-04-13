package app.pillikan.kz.common.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import app.pillikan.kz.R
import org.jetbrains.anko.support.v4.alert

open class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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