package kz.smartideagroup.partner.content.view.delivery.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.helpers.convertTimes
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.content.model.response.delivery.OrderDto
import kz.smartideagroup.partner.content.view.delivery.DeliveryFragment
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.*
import kotlin.collections.ArrayList

class PreparationInProgressAdapter :
    RecyclerView.Adapter<PreparationInProgressAdapter.ViewHolder> {

    companion object {
        const val TAG = "ProgressAdapter"
    }

    private var orderList: ArrayList<OrderDto> = ArrayList()

    private var callback: DeliveryFragment

    constructor(callback: DeliveryFragment) : super() {
        this.callback = callback
    }

    fun addOrderList(orderList: List<OrderDto>) {
        this.orderList.clear()
        this.orderList.addAll(orderList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PreparationInProgressAdapter.ViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.item_runnig_cook, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: PreparationInProgressAdapter.ViewHolder, position: Int) {
        holder.bind(orderDto = orderList[position], callback)
    }

    override fun getItemCount(): Int = orderList.size

    class ViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
        private val tvId = root.findViewById<TextView>(R.id.tv_order_id)
        private val tvReadyFood = root.findViewById<TextView>(R.id.tv_ready_food)
        private val tvTimer = root.findViewById<TextView>(R.id.tv_time)
        private val progressBar = root.findViewById<ProgressBar>(R.id.pb_timer)
        private val view = root.findViewById<View>(R.id.view)
        private val rlProgress = root.findViewById<RelativeLayout>(R.id.rl_progress_bar)
        private val btnReady = root.findViewById<LinearLayout>(R.id.ll_ready)
        private val llCookTimer = root.findViewById<LinearLayout>(R.id.ll_cook_time)

        fun bind(orderDto: OrderDto, callback: DeliveryFragment) {
            tvId.text = orderDto.id.toString()
            setTimer(orderDto)
            itemView.onClick {
                callback.setOnClickItemOrder(orderDto)
            }
            when (orderDto.status) {
                Constants.ORDER_PAY -> {
                    callback.setDoNotAcceptOrder(orderDto)
                }
                Constants.ORDER_COOKING -> {
                    btnReady.onClick {
                        //status 3 accept order
                    }
                }
                Constants.ORDER_SEND -> {
                    rlProgress.visibility = View.GONE
                    view.visibility = View.VISIBLE
                    when (orderDto.type) {
                        Constants.DELIVERY -> tvReadyFood.text = callback.getString(R.string.send_order)
                        Constants.BY_MYSELF ->  tvReadyFood.text = callback.getString(R.string.transfer_to_client)
                    }
                }
                else -> {
                    llCookTimer.visibility = View.GONE
                }
            }

            btnReady.onClick {
                when (orderDto.type) {
                    Constants.DELIVERY -> {
                        when (orderDto.status) {
                            Constants.ORDER_COOKING -> {
                                callback.setStatusOrder(orderDto, Constants.ORDER_SEND)
                            }
                            Constants.ORDER_SEND -> {
                                //alert send delivery
                                callback.alertDialog.alertDialogDelivery(orderDto)
                            }
                        }
                    }
                    Constants.BY_MYSELF -> {
                        when (orderDto.status) {
                            Constants.ORDER_COOKING -> {
                                callback.setStatusOrder(orderDto, Constants.ORDER_SEND)
                            }
                            else -> {
                                callback.alertDialog.alertDialogTakeAway(orderDto)
                            }
                        }
                    }
                }

            }

        }

        private fun setTimer(orderDto: OrderDto) {
            try {
                progressBar.progress = Constants.ZERO
                progressBar.max = Constants.HUNDRED

                val deadlineCook = convertTimes(orderDto.cookingDeadline!!)
                val startCook = convertTimes(orderDto.updatedAt!!)
                val calendar = Calendar.getInstance()
                when (orderDto.status) {
                    Constants.ORDER_COOKING -> {
                        val fullPercentage = (deadlineCook!!.time.time.minus(startCook!!.time.time))
                        val progressPercentage =
                            (((deadlineCook.time.time - calendar.time.time) * Constants.HUNDRED) / fullPercentage).toInt()
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            progressBar.setProgress(progressPercentage, true)
                        } else {
                            progressBar.progress = progressPercentage
                        }
                        val minutes =
                            ((deadlineCook.time.time.minus(calendar.time.time)) / Constants.ONE_THOUSAND) / Constants.SIXTY
                        tvTimer.text = minutes.toInt().toString()
                    }
                }

                root.setOnClickListener(View.OnClickListener {
                    //navigate to DetailedFragment
                })

            } catch (e: Exception) {
                Log.e(TAG, "setTimer: ${e.message}")
            }
        }

    }

}
