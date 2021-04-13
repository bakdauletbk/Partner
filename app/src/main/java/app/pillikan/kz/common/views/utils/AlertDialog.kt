package app.pillikan.kz.common.views.utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import com.google.android.material.button.MaterialButton
import app.pillikan.kz.R
import app.pillikan.kz.common.remote.Constants
import app.pillikan.kz.content.model.response.delivery.OrderDto
import app.pillikan.kz.content.view.delivery.DeliveryFragment

open class AlertDialog {

    private var alertDialog: Dialog? = null

    private var callback: DeliveryFragment

    constructor(callback: DeliveryFragment) : super() {
        this.callback = callback
    }

    fun alertDialogDelivery(order: OrderDto) {
        alertDialog = Dialog(callback.requireContext())
        alertDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog!!.setContentView(R.layout.dialog_delivery)

        val button: MaterialButton = alertDialog!!.findViewById(R.id.btn_delivery_man)

        button.setOnClickListener(View.OnClickListener {
            callback.setLoading(true)
            callback.setStatusOrder(order, Constants.ORDER_DELIVERED)
            alertDialog!!.dismiss()
        })

        alertDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog!!.show()
    }

    fun alertDialogTakeAway(order: OrderDto) {
        alertDialog = Dialog(callback.requireContext())
        alertDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog!!.setContentView(R.layout.dialog_take_away)
        val button: MaterialButton = alertDialog!!.findViewById(R.id.btn_take_away)
        button.setOnClickListener(View.OnClickListener {
            callback.setLoading(true)
            callback.setStatusOrder(order, Constants.ORDER_FINISH)
            alertDialog!!.dismiss()
        })
        alertDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog!!.show()
    }

}