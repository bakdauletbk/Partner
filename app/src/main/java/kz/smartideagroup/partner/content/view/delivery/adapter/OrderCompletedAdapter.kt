package kz.smartideagroup.partner.content.view.delivery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.content.model.response.delivery.OrderDto
import kz.smartideagroup.partner.content.view.delivery.DeliveryFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class OrderCompletedAdapter : RecyclerView.Adapter<OrderCompletedAdapter.ViewHolder> {

    private var orderList: ArrayList<OrderDto> = ArrayList()

    private var callback: DeliveryFragment

    constructor(callback: DeliveryFragment) : super() {
        this.callback = callback
    }

    fun clear(){
        this.orderList.clear()
    }

    fun addOrderList(orderList: List<OrderDto>) {
        this.orderList.addAll(orderList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderCompletedAdapter.ViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.item_finish_cook, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: OrderCompletedAdapter.ViewHolder, position: Int) {
        holder.bind(orderDto = orderList[position], callback)
    }

    override fun getItemCount(): Int = orderList.size

    class ViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {

        private val tvId = root.findViewById<TextView>(R.id.tv_order_id_finish)

        fun bind(orderDto: OrderDto, callback: DeliveryFragment) {
            tvId.text = orderDto.id.toString()

            itemView.onClick {
                callback.setOnClickItemOrder(orderDto)
            }
        }
    }

}