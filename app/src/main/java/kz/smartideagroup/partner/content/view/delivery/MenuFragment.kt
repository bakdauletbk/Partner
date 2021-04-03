package kz.smartideagroup.partner.content.view.delivery

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.fragment_menu.loadingView
import kotlinx.android.synthetic.main.fragment_menu.swipe_refresh_layout
import kotlinx.android.synthetic.main.fragment_notification.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.common.views.BaseFragment
import kz.smartideagroup.partner.content.model.request.delivery.DishStatusRequest
import kz.smartideagroup.partner.content.model.response.delivery.CategoriesItems
import kz.smartideagroup.partner.content.model.response.delivery.DishDto
import kz.smartideagroup.partner.content.view.delivery.adapter.HeaderAdapter
import kz.smartideagroup.partner.content.view.notification.NotificationFragment
import kz.smartideagroup.partner.content.viewmodel.delivery.MenuViewModel
import org.jetbrains.anko.sdk27.coroutines.onClick


class MenuFragment : BaseFragment() {

    private val foodAdapter: HeaderAdapter =
        HeaderAdapter(this)

    private lateinit var viewModel: MenuViewModel

    private var alertDialog: Dialog? = null

    private var refreshTabLayout = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            view?.let { Navigation.findNavController(it).navigate(R.id.deliveryFragment) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initViewModel()
        initSwipeRefreshLayout()
        updateFeed()
        initToolbar()
        initRecyclerView()
        initObservers()
    }

    private fun initSwipeRefreshLayout() {
        swipe_refresh_layout.setOnRefreshListener {
            foodAdapter.clear()
            foodAdapter.notifyDataSetChanged()
            updateFeed()
            swipe_refresh_layout.isRefreshing = false
        }
    }

    private fun initObservers() {
        viewModel.isError.observe(viewLifecycleOwner, {
            setLoading(false)
            errorDialog(getString(R.string.error_no_internet_msg))
        })
        viewModel.foodList.observe(viewLifecycleOwner, {
            when (it) {
                null -> hideMenuFood()
                else -> addFoods(it)
            }
        })
        viewModel.isSendStatus.observe(viewLifecycleOwner, {
            when (it) {
                false -> {
                    setLoading(false)
                    alertDialog!!.dismiss()
                    errorDialog(getString(R.string.error_failed_connection_to_server))
                }
                true -> {
                    alertDialog!!.dismiss()
                    updateFeed()
                }
            }
        })
    }

    private fun initTabLayout(categoryItems: List<CategoriesItems>) {
        when (refreshTabLayout) {
            true -> {
                tabLayout.visibility = View.VISIBLE
                for (i in categoryItems.indices) {
                    tabLayout.addTab(tabLayout.newTab().setText(categoryItems[i].name))
                }
                refreshTabLayout = false
            }
        }
    }

    private fun addFoods(foodItems: List<CategoriesItems>) {
        setLoading(false)
        initTabLayout(foodItems)
        foodAdapter.addFoods(foodItems)
    }

    private fun hideMenuFood() {
        setLoading(false)
        errorDialog(getString(R.string.error_failed_connection_to_server))
        rv_foods.visibility = View.GONE
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)
    }

    private fun updateFeed() {
        setLoading(true)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getFoods()
        }
    }

    private fun initRecyclerView() {
        rv_foods.adapter = foodAdapter
        rv_foods.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val manager = rv_foods.layoutManager as LinearLayoutManager
                val firstVisiblePosition = manager.findFirstVisibleItemPosition()

                tabLayout.tabIndicatorAnimationMode = TabLayout.INDICATOR_ANIMATION_MODE_ELASTIC
                tabLayout.setScrollPosition(firstVisiblePosition, Constants.ZERO_FLOAT, true)
                tabLayout.isSmoothScrollingEnabled = true

                tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        val tabSelectPosition = tabLayout.selectedTabPosition
                        manager.scrollToPositionWithOffset(tabSelectPosition, Constants.ZERO)
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                    }
                })

            }
        })


    }

    private fun initToolbar() {
        toolbarMenu.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbarMenu.setTitleTextColor(Color.WHITE)
        toolbarMenu.setNavigationOnClickListener {
            view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.deliveryFragment) }
        }
    }

    fun foodAlertDialog(dishList: DishDto) {
        alertDialog = Dialog(requireContext())
        alertDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog!!.setContentView(R.layout.alert_dialog_food)

        val tvTitleFood = alertDialog!!.findViewById(R.id.tvTitleFood) as TextView

        val ivFoodRetail = alertDialog!!.findViewById(R.id.ivFoodRetail) as ImageView
        val llClose = alertDialog!!.findViewById(R.id.ll_close) as LinearLayout

        Glide
            .with(requireContext())
            .load(Constants.BASE_IMAGE_URL + dishList.img)
            .centerCrop()
            .into(ivFoodRetail)

        tvTitleFood.text = dishList.name

        val llClick: LinearLayout = alertDialog!!.findViewById(R.id.llOrder)
        val llOffOder: LinearLayout = alertDialog!!.findViewById(R.id.ll_off_food)
        val llOnOder: LinearLayout = alertDialog!!.findViewById(R.id.ll_dish_on_food)
        val llLoading = alertDialog!!.findViewById(R.id.ll_loading) as LinearLayout

        when (dishList.status) {
            Constants.ONE -> {
                llOffOder.visibility = View.GONE
                llOnOder.visibility = View.VISIBLE
            }
            Constants.TWO -> {
                llOnOder.visibility = View.GONE
                llOffOder.visibility = View.VISIBLE
            }
        }

        llClick.onClick {
            llOffOder.visibility = View.GONE
            llOnOder.visibility = View.GONE
            llLoading.visibility = View.VISIBLE
            setDishStatus(dishList)
        }

        llClose.onClick {
            alertDialog!!.dismiss()
        }

        alertDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog!!.show()
    }

    private fun setDishStatus(dishDto: DishDto) {
        val dishStatusRequest = DishStatusRequest(
            dishId = dishDto.id,
            status = if (dishDto.status == Constants.ONE) Constants.TWO else Constants.ONE
        )
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.setDishStatus(dishStatusRequest)
        }
    }

    private fun setLoading(loading: Boolean) {
        loadingView.visibility = if (loading) View.VISIBLE else View.GONE
    }

}