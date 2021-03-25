package kz.smartideagroup.partner.content.view.reports.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.helpers.convertTimes
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.content.model.response.reports.ReportsItems
import java.util.*
import kotlin.collections.ArrayList

class ReportsAdapter(private val reportList: ArrayList<ReportsItems>) :
    RecyclerView.Adapter<ReportsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repost, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reportList: ReportsItems = reportList[position]

        reportList.name.let {
            holder.tvName.text = it
        }

        when (reportList.type) {
            Constants.ZERO -> holder.ivReports.setBackgroundResource(R.drawable.ic_arrow_pay_icon)
            Constants.ONE -> {
                holder.ivReports.setBackgroundResource(R.drawable.ic_delivery_pay_icon)
                holder.tvName.text = Constants.ORDER + reportList.orderId.toString()
            }
        }

        reportList.phone.let {
            holder.tvPhone.text = it
        }
        reportList.phone.let {
            val phone: String = it.toString()
            val charPhone: CharArray = phone.toCharArray()
            holder.tvPhone.text =
                "${charPhone[0]}${charPhone[1]}${charPhone[2]}${charPhone[3]} *** ** ${charPhone[9]}${charPhone[10]}"
        }
        reportList.amount.let {
            holder.tvAmount.text = Constants.PLUS + it
        }
        reportList.commissionAmount.let {
            holder.tvCommissionAmount.text = Constants.MINUS + it
        }
        reportList.realAmount.let {
            holder.tvRealAmount.text = it.toString()
        }
//        reportList.createdAt.let {
//            val calendar = convertTimes(it!!)
//            holder.tvDate.text =
//                calendar?.get(Calendar.DAY_OF_MONTH).toString() + Constants.DOT +
//                        (calendar?.get(Calendar.MONTH)!!.plus(Constants.ONE)).toString() + Constants.DOT +
//                        calendar.get(Calendar.YEAR).toString()
//        }
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.findViewById(R.id.tv_name) as TextView
        val tvPhone = itemView.findViewById(R.id.tv_phone) as TextView
        val tvCommission = itemView.findViewById(R.id.tv_commission) as TextView
        val tvDate = itemView.findViewById(R.id.tv_date) as TextView
        val tvAmount = itemView.findViewById(R.id.tv_amount) as TextView
        val tvCommissionAmount = itemView.findViewById(R.id.tv_commission_amount) as TextView
        val tvRealAmount = itemView.findViewById(R.id.tv_real_amount) as TextView
        val ivReports = itemView.findViewById(R.id.iv_reports) as ImageView
    }
}