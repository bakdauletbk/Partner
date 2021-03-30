package kz.smartideagroup.partner.content.view.delivery

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_delivery.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.common.views.BaseFragment
import kz.smartideagroup.partner.common.views.PaginationCompletedScrollListener
import kz.smartideagroup.partner.common.views.utils.AlertDialog
import kz.smartideagroup.partner.content.model.request.delivery.DeliveryStatusRequest
import kz.smartideagroup.partner.content.model.request.delivery.ReqOrderStatus
import kz.smartideagroup.partner.content.model.response.delivery.OrderDto
import kz.smartideagroup.partner.content.model.response.delivery.RetailDto
import kz.smartideagroup.partner.content.view.accept_order.AcceptOrderActivity
import kz.smartideagroup.partner.content.view.delivery.adapter.OrderCompletedAdapter
import kz.smartideagroup.partner.content.view.delivery.adapter.PreparationInProgressAdapter
import kz.smartideagroup.partner.content.viewmodel.delivery.DeliveryViewModel
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.intentFor

class DeliveryFragment : BaseFragment() {

    companion object {
        const val PERFORMED = "Выполняется ("
        const val ONE = 1
    }

    private var nextPage = ONE
    private val bundle = Bundle()
    private lateinit var viewModel: DeliveryViewModel
    private val orderActiveAdapter: PreparationInProgressAdapter =
        PreparationInProgressAdapter(this)

    private val orderCompleted: OrderCompletedAdapter = OrderCompletedAdapter(this)
    private var retailInfo: RetailDto? = null

    val alertDialog: AlertDialog = AlertDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_delivery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initViewModel()
        initRecyclerView()
        updateFeed()
        initListeners()
        initObservers()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(DeliveryViewModel::class.java)
    }

    private fun initRecyclerView() {
        rv_order_active.adapter = orderActiveAdapter
        rv_order_active.apply {
            layoutManager = LinearLayoutManager(context)
        }

        rv_order_finish.adapter = orderCompleted
        rv_order_finish.addOnScrollListener(object :
            PaginationCompletedScrollListener(rv_order_finish.layoutManager as LinearLayoutManager) {
            override fun isLastPage(): Boolean = viewModel.isHasNext()
            override fun isLoading(): Boolean = viewModel.isLoading()
            override fun loadMoreItems() {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.getNextPage(nextPage)
                    nextPage++
                }
            }
        })
    }

    private fun updateFeed() {
        setLoading(true)
        getRetailInfo()
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getOrderActive()
        }
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getInitialPage()
        }
    }

    private fun getRetailInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getRetailInfo()
        }
    }

    private fun initListeners() {
        rl_menu.onClick {
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_deliveryFragment_to_menuFragment)
            }
        }
        rl_report_delivery.onClick {
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.reportsFragment)
            }
        }
        ll_delivery_status.onClick {
            setDeliveryStatus()
        }
    }

    //Adapter click item
    fun setOnClickItemOrder(orderDto: OrderDto) {
        bundle.putSerializable(Constants.SERIALIZED_ORDER_COMPLETED, orderDto)
        view?.let { it1 ->
            Navigation.findNavController(it1)
                .navigate(R.id.action_deliveryFragment_to_detailPageFragment, bundle)
        }
    }

    //Adapter Status Order callback
    fun setStatusOrder(id: Int, status: Int) {
        setLoading(true)
        val reqOrderStatus = ReqOrderStatus(orderId = id, status = status)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.setOrderStatus(reqOrderStatus)
        }
    }

    //Order status = 2 Adapter do not accept order
    fun setDoNotAcceptOrder(orderDto: OrderDto) {
        startActivity(
            intentFor<AcceptOrderActivity>().putExtra(
                Constants.ACCEPT_ORDER_ID,
                orderDto.id
            )
        )
        activity?.finish()
    }

    private fun setDeliveryStatus() {
        setLoading(true)
        val status =
            if (retailInfo!!.status == Constants.DELIVERY_OPEN) Constants.DELIVERY_CLOSE else Constants.DELIVERY_OPEN
        val deliveryStatusRequest = DeliveryStatusRequest(status)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.setDeliveryStatus(deliveryStatusRequest)
        }
    }

    private fun initObservers() {
        viewModel.isError.observe(viewLifecycleOwner, {
            setLoading(false)
            errorDialog(it)
        })
        viewModel.orderActive.observe(viewLifecycleOwner, {
            when (it) {
                null -> {
                    setLoading(false)
                    errorDialog(getString(R.string.error_no_internet_msg))
                }
                else -> addOrderActive(it)
            }
        })
        viewModel.retailInfo.observe(viewLifecycleOwner, {
            when (it) {
                null ->{
                    setLoading(false)
                    errorDialog(getString(R.string.error_no_internet_msg))
                }
                else -> {
                    setLoading(false)
                    setRetailInfo(it)
                }
            }
        })
        viewModel.isDeliveryStatus.observe(viewLifecycleOwner, {
            when (it) {
                true -> {
                    setLoading(false)
                    getRetailInfo()
                }
                false ->{
                    setLoading(false)
                    errorDialog(getString(R.string.error_no_internet_msg))
                }
            }
        })
        viewModel.orderCompletedList.observe(viewLifecycleOwner, {
            when (it) {
                null -> {
                    setLoading(false)
                    errorDialog(getString(R.string.error_no_internet_msg))
                }
                else -> {
                    setLoading(false)
                    orderCompleted.addOrderList(it)
                }
            }
        })
        viewModel.isOrderStatus.observe(viewLifecycleOwner, {
            when (it) {
                true -> {
                    setLoading(false)
                    orderActiveAdapter.notifyDataSetChanged()
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

    private fun setRetailInfo(retailDto: RetailDto) {
        retailInfo = retailDto
        when (retailDto.status) {
            Constants.DELIVERY_CLOSE -> setColorDeliveryStatus(
                R.drawable.shape_close_delivery,
                getString(R.string.close_delivery)
            )
            Constants.DELIVERY_OPEN -> setColorDeliveryStatus(
                R.drawable.shape_open_delivery,
                getString(R.string.open_deliver)
            )
        }
    }

    private fun setColorDeliveryStatus(drawable: Int, deliveryText: String) {
        ll_delivery_status.setBackgroundResource(drawable)
        view?.let { Snackbar.make(it, deliveryText, Snackbar.LENGTH_SHORT).show() }
    }

    @SuppressLint("SetTextI18n")
    private fun addOrderActive(orderList: List<OrderDto>) {
        setLoading(false)
        tv_performed.text = PERFORMED + orderList.size.toString() + ")"
        orderActiveAdapter.addOrderList(orderList)
    }

    fun setLoading(loading: Boolean) {
        loadingView.visibility = if (loading) View.VISIBLE else View.GONE
        rl_menu.isEnabled = !loading
        rl_report_delivery.isEnabled = !loading
        ll_delivery_status.isEnabled = !loading
    }

}