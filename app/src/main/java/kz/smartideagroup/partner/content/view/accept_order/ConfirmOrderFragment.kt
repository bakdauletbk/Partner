package kz.smartideagroup.partner.content.view.accept_order

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_confirm_order.*
import kotlinx.android.synthetic.main.fragment_confirm_order.ll_comment
import kotlinx.android.synthetic.main.fragment_confirm_order.tv_comment
import kotlinx.android.synthetic.main.fragment_confirm_order.tv_count
import kotlinx.android.synthetic.main.fragment_confirm_order.tv_total_price
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.helpers.convertTimes
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.common.views.BaseFragment
import kz.smartideagroup.partner.common.views.utils.FoodPreparationTime
import kz.smartideagroup.partner.content.model.request.delivery.ReqOrderStatus
import kz.smartideagroup.partner.content.model.response.delivery.OrderDto
import kz.smartideagroup.partner.content.view.FoundationActivity
import kz.smartideagroup.partner.content.view.accept_order.adapter.FoodAdapter
import kz.smartideagroup.partner.content.viewmodel.accept_order.ConfirmOrderViewModel
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.intentFor
import java.util.*

class ConfirmOrderFragment : BaseFragment() {

    private lateinit var viewModel: ConfirmOrderViewModel

    private val foodPreparation: FoodPreparationTime = FoodPreparationTime(this)

    private lateinit var countDownTimer: CountDownTimer

    private var timerRun: Boolean? = null

    private var alertDialog: Dialog? = null

    private val foodAdapter: FoodAdapter =
        FoodAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_confirm_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initViewModel()
        initRecyclerView()
        getOrderInfo()
        initListeners()
        initObservers()
    }

    private fun initRecyclerView() {
        rv_food_items.adapter = foodAdapter
        rv_food_items.apply {
            layoutManager = LinearLayoutManager(context)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getOrderInfo() {
        val orderId = arguments?.getInt(Constants.ACCEPT_ORDER_ID)
        tv_id.text = "Заказ $orderId"
        setLoading(true)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getOrder(orderId!!)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ConfirmOrderViewModel::class.java)
    }

    private fun initListeners() {
        foodPreparation.onClickTime()
        iv_error.onClick {
            retailAppealDialog()
        }
    }

    fun setOrderStatus(timeCook: Int) {

        val orderId = arguments?.getInt(Constants.ACCEPT_ORDER_ID)

        val reqOrderStatus = ReqOrderStatus(
            orderId = orderId,
            status = Constants.ORDER_COOKING,
            timeInMinutes = timeCook
        )
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.setOrderStatus(reqOrderStatus)
        }
    }

    private fun retailAppealDialog() {
        alertDialog = Dialog(requireContext())
        alertDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog!!.setContentView(R.layout.alert_retail_appeal)

        val ivCancel = alertDialog!!.findViewById(R.id.ivCancel) as ImageView

        ivCancel.onClick {
            alertDialog!!.cancel()
        }

        alertDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        alertDialog!!.show()

        val window: Window = alertDialog!!.window!!
        window.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
    }


    private fun initObservers() {
        viewModel.isError.observe(viewLifecycleOwner, {
            setLoading(false)
            errorDialog(it)
        })
        viewModel.order.observe(viewLifecycleOwner, {
            when (it) {
                null -> {
                    setLoading(false)
                    Toast.makeText(
                        context,
                        getString(R.string.error_receiving_order),
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                    setLoading(false)
                    setOrderInfo(it)
                }
            }
        })
        viewModel.isOrderStatus.observe(viewLifecycleOwner, {
            when (it) {
                true -> {
                    setLoading(false)
                    startActivity(intentFor<FoundationActivity>())
                }
                false -> {
                    setLoading(false)
                    Toast.makeText(
                        context,
                        getString(R.string.unfortunately_your_status_has_not_been_sent),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setOrderInfo(orderDto: OrderDto) {
        tv_id.text = Constants.ORDER + orderDto.id.toString()
        tv_total_price.text = orderDto.foodAmount?.toInt().toString()

        foodAdapter.addOrderList(orderDto.orderItems)

        val calendar = Calendar.getInstance()
        startTimer(
            (convertTimes(orderDto.createdAt!!)!!.time.time.plus(Constants.TIME_MILLIS)).minus(
                calendar.time.time
            ),
            tv_time_to_confirm
        )

        when (orderDto.description!!.length > Constants.ONE) {
            true -> {
                ll_comment.visibility = View.VISIBLE
                tv_comment.text = orderDto.description
            }
        }

        tv_count.text = Constants.UTENSILS_COUNT + orderDto.utensils.toString()
    }

    private fun startTimer(TimerAll: Long, textView: TextView) {

        countDownTimer = object : CountDownTimer(TimerAll, Constants.ONE_THOUSAND) {

            override fun onTick(millisUntilFinished: Long) {
                Constants.TIME_LEFT_IN_MILLIS = millisUntilFinished.toInt()

                val minutes =
                    (Constants.TIME_LEFT_IN_MILLIS / Constants.ONE_THOUSAND) / Constants.SIXTY
                val seconds =
                    (Constants.TIME_LEFT_IN_MILLIS / Constants.ONE_THOUSAND) % Constants.SIXTY

                val time = "${minutes}:${seconds}"
                textView.text = time
            }

            override fun onFinish() {
                timerRun = false
                btn_order_accept.setBackgroundColor(resources.getColor(R.color.red_color))
                iv_error.visibility = View.VISIBLE
                textView.text = Constants.TIME_DEFAULT
            }
        }.start()
        timerRun = true
    }

    fun setLoading(loading: Boolean) {
        loadingViews.visibility = if (loading) View.VISIBLE else View.GONE
        btn_order_accept.isEnabled = !loading
    }

}