package kz.smartideagroup.partner.content.view.accept_order.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.content.model.response.delivery.OrderItems
import kz.smartideagroup.partner.content.view.accept_order.ConfirmOrderFragment
import kz.smartideagroup.partner.content.view.delivery.DetailPageFragment

class FoodAdapter : RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private var orderList: ArrayList<OrderItems> = ArrayList()

    private var callback: ConfirmOrderFragment

    constructor(callback: ConfirmOrderFragment) : super() {
        this.callback = callback
    }

    fun addOrderList(orderList: List<OrderItems>) {
        this.orderList.addAll(orderList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.item_food_count, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orderItems = orderList[position], callback)
    }

    override fun getItemCount(): Int = orderList.size

    class ViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
        private val tvFoodName = root.findViewById<TextView>(R.id.tv_name_foods)
        private val tvPrice = root.findViewById<TextView>(R.id.tv_price_food)

        @SuppressLint("SetTextI18n")
        fun bind(orderItems: OrderItems, callback: ConfirmOrderFragment) {
            tvFoodName.text = orderItems.dish?.name + " x${orderItems.quantity}"
            tvPrice.text = orderItems.quantity!!.times(orderItems.dish!!.price!!).toInt().toString()
        }
    }

}