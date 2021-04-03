package kz.smartideagroup.partner.common.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kz.smartideagroup.partner.content.view.accept_order.AcceptOrderActivity

class PushHandlerReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context!!.startActivity(Intent(context, AcceptOrderActivity::class.java))
    }

}