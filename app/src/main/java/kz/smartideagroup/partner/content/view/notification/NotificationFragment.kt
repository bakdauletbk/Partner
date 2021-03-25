package kz.smartideagroup.partner.content.view.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_notification.*
import kotlinx.android.synthetic.main.fragment_notification.loadingView
import kotlinx.android.synthetic.main.fragment_report_view_pager.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.views.BaseFragment
import kz.smartideagroup.partner.common.views.PaginationScrollListener
import kz.smartideagroup.partner.content.model.response.notification.RetailNotifications
import kz.smartideagroup.partner.content.viewmodel.notification.NotificationViewModel

class NotificationFragment : BaseFragment() {

    companion object {
        const val ONE = 1
        const val ZERO = 0
    }

    private lateinit var viewModel: NotificationViewModel
    private val adapter: NotificationAdapter = NotificationAdapter(this)
    private var currentCount: Int = ZERO
    private var nextPage = ONE

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
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initViewModel()
        initRecyclerView()
        updateFeed()
        initObservers()
    }

    private fun initRecyclerView() {
        rv_notification.adapter = adapter
        rv_notification.addOnScrollListener(object :
            PaginationScrollListener(rv_notification.layoutManager as LinearLayoutManager) {
            override fun totalCount(): Int {
                return viewModel.totalCount()
            }

            override fun currentCount(): Int {
                return currentCount
            }

            override fun isLoading(): Boolean {
                return viewModel.isLoading()
            }

            override fun loadMoreItems() {
                setLoading(true)
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.getNextPage(nextPage)
                    nextPage++
                }
            }
        })
    }

    private fun initObservers() {
        viewModel.isError.observe(viewLifecycleOwner, {
            errorDialog(getString(R.string.error_unknown_body))
        })
        viewModel.notificationList.observe(viewLifecycleOwner, {
            when (it) {
                null -> setEmptyReports()
                else -> addNotifications(it)
            }
        })
    }

    private fun setEmptyReports() {
        setLoading(false)
        rv_notification.visibility = View.GONE
        tv_empty_notification.visibility = View.VISIBLE
    }

    private fun addNotifications(notificationList: List<RetailNotifications>) {
        setLoading(false)
        adapter.addNotifications(notificationList = notificationList)
    }

    fun setCurrentCount(count: Int) {
        currentCount = count
    }

    private fun updateFeed() {
        setLoading(true)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getInitialPage()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(NotificationViewModel::class.java)
    }

    private fun setLoading(loading: Boolean) {
        loadingView.visibility = if (loading) View.VISIBLE else View.GONE
    }
}