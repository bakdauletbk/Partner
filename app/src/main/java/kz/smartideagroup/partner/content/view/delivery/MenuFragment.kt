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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.common.views.BaseFragment
import kz.smartideagroup.partner.content.model.response.delivery.CategoriesItems
import kz.smartideagroup.partner.content.model.response.delivery.DishDto
import kz.smartideagroup.partner.content.view.delivery.adapter.HeaderAdapter
import kz.smartideagroup.partner.content.viewmodel.delivery.MenuViewModel
import org.jetbrains.anko.sdk27.coroutines.onClick

class MenuFragment : BaseFragment() {

    private val foodAdapter: HeaderAdapter =
        HeaderAdapter(this)

    private lateinit var viewModel: MenuViewModel

    private var alertDialog: Dialog? = null

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
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initViewModel()
        updateFeed()
        initToolbar()
        initRecyclerView()
        initObservers()
    }

    private fun initObservers() {
        viewModel.isError.observe(viewLifecycleOwner, {
            errorDialog(it)
        })
        viewModel.foodList.observe(viewLifecycleOwner, {
            when (it) {
                null -> hideMenuFood()
                else -> {
                    addFoods(it)
                }
            }
        })
    }

    private fun initTabLayout(categoryItems: List<CategoriesItems>) {

        tabLayout.visibility = View.VISIBLE
        for (i in categoryItems.indices) {
            tabLayout.addTab(tabLayout.newTab().setText(categoryItems[i].name))
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun addFoods(foodItems: List<CategoriesItems>) {
        setLoading(false)
        initTabLayout(foodItems)
        setRecyclerView()
        foodAdapter.addFoods(foodItems)

    }

    private fun setRecyclerView() {
        val isScrolled = false
        val manager = LinearLayoutManager(requireContext())
//        manager.scrollToPositionWithOffset(str1[pos], 0);

        rv_foods.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isScrolled) {
//                    val top = manager.findFirstVisibleItemPosition()
//                    val bottom = manager.findLastVisibleItemPosition()
//
//                    var pos = 0;
//                    if (bottom == categoryList?.size!!.minus(1)) {
//                        pos = str1.length - 1;
//                    } else if (top == str1[str1.length - 1]) {
//                         If top is equal to the specified position, you can correspond to the tab,
//                        pos = str1[str1.length - 1];
//                    } else {
//                        for (int i = 0; i < str1.length - 1; i++) {
//                            if (top == str1[i]) {
//                                pos = i;
//                                break;
//                            } else if (top > str1[i] && top < str1[i + 1]) {
//                                pos = i;
//                                break;
//                            }
//                        }
//                }
//
//                 Set the tab to the pos
//                    tabLayout.setScrollPosition(pos, 0f, true)
                }
            }
        })

    }

    private fun hideMenuFood() {
        setLoading(false)
        rv_foods.visibility = View.GONE
        tv_empty_menu.visibility = View.VISIBLE
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
        rv_foods.apply {
            layoutManager = LinearLayoutManager(context)
        }
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
        val llOffOder: LinearLayout = alertDialog!!.findViewById(R.id.llOffFood)
        val llOnOder: LinearLayout = alertDialog!!.findViewById(R.id.llOnFood)

        if (dishList.status == Constants.ONE) {
            llOffOder.visibility = View.GONE
            llOnOder.visibility = View.VISIBLE
        } else if (dishList.status == Constants.TWO) {
            llOnOder.visibility = View.GONE
            llOffOder.visibility = View.VISIBLE
        }

        llClick.onClick {
//            setDishStatus(dishList, adapter)
        }

        llClose.onClick {
            alertDialog!!.dismiss()
        }

        alertDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog!!.show()
    }

    private fun setLoading(loading: Boolean) {
        loadingView.visibility = if (loading) View.VISIBLE else View.GONE
    }

}