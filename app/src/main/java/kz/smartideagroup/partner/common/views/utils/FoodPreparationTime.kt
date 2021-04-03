package kz.smartideagroup.partner.common.views.utils

import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.content.view.accept_order.ConfirmOrderFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

open class FoodPreparationTime {

    private var callback: ConfirmOrderFragment

    constructor(callback: ConfirmOrderFragment) : super() {
        this.callback = callback
    }

    fun onClickTime() {
        var timeCook: Int? = null

        val btn1 = callback.view?.findViewById<LinearLayout>(R.id.btn_minute_ten)
        val btn2 = callback.view?.findViewById<LinearLayout>(R.id.btn_minute_twenty)
        val btn3 = callback.view?.findViewById<LinearLayout>(R.id.btn_minute_thirty)
        val btn4 = callback.view?.findViewById<LinearLayout>(R.id.btn_minute_forty)
        val btn5 = callback.view?.findViewById<LinearLayout>(R.id.btn_minute_fifty)

        val btnStatusOrder = callback.view?.findViewById<MaterialButton>(R.id.btn_order_accept)

        btn1?.onClick {
            timeCook = Constants.TEN_MINUTE
            btnStatusOrder?.setBackgroundColor(callback.resources.getColor(R.color.blue))
            btn1.isEnabled = false
            btn2?.isEnabled = true
            btn3?.isEnabled = true
            btn4?.isEnabled = true
            btn5?.isEnabled = true
        }
        btn2?.onClick {
            timeCook = Constants.TWENTY_MINUTE
            btnStatusOrder?.setBackgroundColor(callback.resources.getColor(R.color.blue))
            btn2.isEnabled = false
            btn1?.isEnabled = true
            btn3?.isEnabled = true
            btn4?.isEnabled = true
            btn5?.isEnabled = true
        }
        btn3?.onClick {
            timeCook = Constants.THIRTY_MINUTE
            btnStatusOrder?.setBackgroundColor(callback.resources.getColor(R.color.blue))
            btn3.isEnabled = false
            btn2?.isEnabled = true
            btn1?.isEnabled = true
            btn4?.isEnabled = true
            btn5?.isEnabled = true
        }
        btn4?.onClick {
            timeCook = Constants.FORTY_MINUTE
            btnStatusOrder?.setBackgroundColor(callback.resources.getColor(R.color.blue))
            btn4.isEnabled = false
            btn2?.isEnabled = true
            btn3?.isEnabled = true
            btn1?.isEnabled = true
            btn5?.isEnabled = true
        }
        btn5?.onClick {
            timeCook = Constants.FIFTY_MINUTE
            btnStatusOrder?.setBackgroundColor(callback.resources.getColor(R.color.blue))
            btn5.isEnabled = false
            btn2?.isEnabled = true
            btn3?.isEnabled = true
            btn4?.isEnabled = true
            btn1?.isEnabled = true
        }

        btnStatusOrder?.onClick {
            when (timeCook != null) {
                true -> {
                    callback.setLoading(true)
                    callback.setOrderStatus(timeCook!!)
                }
                false ->
                    Toast.makeText(
                        callback.context,
                        callback.getString(R.string.take_time_to_prepare_your_meal),
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }


    }

}