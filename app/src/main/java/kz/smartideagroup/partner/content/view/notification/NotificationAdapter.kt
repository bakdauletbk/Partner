package kz.smartideagroup.partner.content.view.notification

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.content.model.response.notification.RetailNotifications

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private var notificationList: ArrayList<RetailNotifications> = ArrayList()

    private var callback: NotificationFragment

    constructor(callback: NotificationFragment) : super() {
        this.callback = callback
    }

    fun addNotifications(notificationList: List<RetailNotifications>) {
        this.notificationList.addAll(notificationList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationAdapter.ViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: NotificationAdapter.ViewHolder, position: Int) {
        holder.bind(notificationList[position], callback)
        callback.setCurrentCount(notificationList.size)
    }

    override fun getItemCount(): Int = notificationList.size

    class ViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
        private val tvTitle = root.findViewById<TextView>(R.id.tv_title)
        private val tvDescription = root.findViewById<TextView>(R.id.tv_description)
        private val tvDate = root.findViewById<TextView>(R.id.tv_data_at_time)

        @SuppressLint("SetTextI18n")
        fun bind(notification: RetailNotifications, callback: NotificationFragment) {
            tvTitle.text = notification.title
            tvDescription.text = notification.description
            val dataAt = notification.createdAt
            val dataAtTime: CharArray = dataAt!!.toCharArray()
            tvDate.text =
                "${dataAtTime[8]}${dataAtTime[9]}.${dataAtTime[5]}${dataAtTime[6]}.${dataAtTime[0]}${dataAtTime[1]}${dataAtTime[2]}${dataAtTime[3]}  ${dataAtTime[11]}${dataAtTime[12]}${dataAtTime[13]}${dataAtTime[14]}${dataAtTime[15]}"

        }
    }
}