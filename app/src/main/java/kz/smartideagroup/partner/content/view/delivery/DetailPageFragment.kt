package kz.smartideagroup.partner.content.view.delivery

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_detail_page.*
import kotlinx.android.synthetic.main.fragment_detail_page.toolbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.helpers.convertTimes
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.common.views.BaseFragment
import kz.smartideagroup.partner.content.model.request.delivery.ReqOrderStatus
import kz.smartideagroup.partner.content.model.response.delivery.OrderDto
import kz.smartideagroup.partner.content.view.delivery.adapter.FoodCountAdapter
import kz.smartideagroup.partner.content.viewmodel.delivery.DetailPageViewModel
import org.jetbrains.anko.sdk27.coroutines.onClick

class DetailPageFragment : BaseFragment() {

    private var orderDto: OrderDto? = null

    private lateinit var viewModel: DetailPageViewModel

    private val orderAdapter: FoodCountAdapter =
        FoodCountAdapter(this)

    companion object {
        const val TAG = "DetailPageFragment"
        const val SECOND = 60000
        const val MIN = " мин"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.deliveryFragment) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initToolbar()
        initViewModel()
        initRecyclerView()
        setOrderInfo()
        initListeners()
        initObservers()
    }

    private fun initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationOnClickListener {
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.deliveryFragment)
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(DetailPageViewModel::class.java)
    }

    private fun initObservers() {
        viewModel.isError.observe(viewLifecycleOwner, {
            errorDialog(getString(R.string.error_no_internet_msg))
        })
        viewModel.isOrderStatus.observe(viewLifecycleOwner, {
            when (it) {
                true -> {
                    setLoading(false)
                    when (orderDto?.status) {
                        Constants.ORDER_COOKING -> orderDto?.status = Constants.ORDER_SEND
                        Constants.ORDER_SEND -> orderDto?.status = Constants.ORDER_DELIVERED
                    }
                    setOrderInfo()
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

    private fun initListeners() {
        btn_print.onClick {
            Toast.makeText(context, getString(R.string.no_printer_found), Toast.LENGTH_LONG).show()
        }
        btn_ready.onClick {
            prepareOrderStatus()
        }
    }

    private fun prepareOrderStatus() {
        when (orderDto!!.type) {
            Constants.DELIVERY -> setDeliveryOrderStatus()
            Constants.BY_MYSELF -> setByMySelfOrderStatus()
        }
    }

    private fun setByMySelfOrderStatus() {
        when (orderDto!!.status) {
            Constants.ORDER_COOKING -> setOrderStatus(Constants.ORDER_SEND)
            Constants.ORDER_SEND -> setOrderStatus(Constants.ORDER_FINISH)
        }
    }

    private fun setDeliveryOrderStatus() {
        when (orderDto!!.status) {
            Constants.ORDER_COOKING -> setOrderStatus(Constants.ORDER_SEND)
            Constants.ORDER_SEND -> setOrderStatus(Constants.ORDER_DELIVERED)
            Constants.ORDER_DELIVERED -> setOrderStatus(Constants.ORDER_FINISH)
        }
    }

    private fun setOrderStatus(status: Int) {
        setLoading(true)
        val reqOrderStatus = ReqOrderStatus(orderId = orderDto!!.id, status = status)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.setOrderStatus(reqOrderStatus)
        }
    }

    private fun initRecyclerView() {
        rv_count_dish.adapter = orderAdapter
        rv_count_dish.apply {
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setOrderInfo() {
        orderDto = arguments?.getSerializable(
            Constants.SERIALIZED_ORDER_COMPLETED
        ) as OrderDto

        //add list Adapter
        orderAdapter.addOrderList(orderDto!!.orderItems)

        tv_order_id.text = orderDto!!.id.toString()
        tv_count.text = orderDto!!.utensils.toString()
        tv_address_detail.text = orderDto!!.address
        tv_total_price.text = orderDto!!.foodAmount?.toInt().toString()

        when (orderDto!!.description!!.length > Constants.ONE) {
            true -> {
                ll_comment.visibility = View.VISIBLE
                tv_comment.text = orderDto!!.description
            }
        }

        when (orderDto?.status) {
            Constants.ORDER_COOKING -> tv_status_order.text = getString(R.string.food_prepare)
            Constants.ORDER_SEND -> {
                tv_status_order.text = getString(R.string.prepared)
                setTypeOrder(orderDto!!.type!!)
            }
            Constants.ORDER_DELIVERED -> {
                tv_status_order.text = getString(R.string.on_delivery)
                btn_ready.visibility = View.GONE
            }
            Constants.ORDER_FINISH -> {
                tv_status_order.text = getString(R.string.completed)
                btn_ready.visibility = View.GONE
            }
        }

        setOrderCookTime(orderDto!!)
    }

    @SuppressLint("SetTextI18n")
    private fun setOrderCookTime(orderDto: OrderDto) {
        try {
            tv_cook_time.text =
                ((convertTimes(orderDto.cookingDeadline!!)!!.time.time - convertTimes(orderDto.createdAt!!)!!.time.time) / SECOND).toString() + MIN
        } catch (e: NullPointerException) {
            Log.e(TAG, "setOrderCookTime: ${e.message}")
        }
    }

    private fun setTypeOrder(type: Int) {
        when (type) {
            Constants.DELIVERY -> btn_ready.text = getString(R.string.send_order)
            Constants.BY_MYSELF -> btn_ready.text = getString(R.string.transfer_to_client)
        }
    }

    private fun setLoading(loading: Boolean) {
        loadingView.visibility = if (loading) View.VISIBLE else View.GONE
        btn_print.isEnabled = !loading
        btn_ready.isEnabled = !loading
    }

}