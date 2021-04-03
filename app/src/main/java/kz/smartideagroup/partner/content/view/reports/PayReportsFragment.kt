package kz.smartideagroup.partner.content.view.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_report_view_pager.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.common.views.BaseFragment
import kz.smartideagroup.partner.common.views.PaginationCompletedScrollListener
import kz.smartideagroup.partner.content.model.response.reports.ReportsItems
import kz.smartideagroup.partner.content.view.reports.adapter.ReportsAdapter
import kz.smartideagroup.partner.content.viewmodel.reports.AllReportsViewModel
import kz.smartideagroup.partner.content.viewmodel.reports.PayReportsViewModel

class PayReportsFragment : BaseFragment() {

    private var reportList: ArrayList<ReportsItems> = arrayListOf()
    private val reportAdapter by lazy { ReportsAdapter(reportList) }
    private lateinit var viewModel: PayReportsViewModel
    private var nextPage = Constants.ONE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_report_view_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initViewModel()
        initRecyclerView()
        initSwipeRefreshLayout()
        updateFeed()
        initObservers()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(PayReportsViewModel::class.java)
    }

    private fun initSwipeRefreshLayout() {
        swipe_refresh_layout.setOnRefreshListener {
            reportList.clear()
            reportAdapter.notifyDataSetChanged()
            nextPage = Constants.ONE
            updateFeed()
            swipe_refresh_layout.isRefreshing = false
        }
    }


    private fun initRecyclerView() {
        rv_report.adapter = reportAdapter
        rv_report.addOnScrollListener(object :
            PaginationCompletedScrollListener(rv_report.layoutManager as LinearLayoutManager) {
            override fun isLastPage(): Boolean = viewModel.isHasNext()
            override fun isLoading(): Boolean = viewModel.isLoading()
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
            setLoading(false)
            errorDialog(getString(R.string.error_no_internet_msg))
        })
        viewModel.reportList.observe(viewLifecycleOwner, {
            if (it != null) {
                setLoading(false)
                addReports(it)
            } else {
                setEmptyReports()
            }
        })
    }

    private fun addReports(reportList: List<ReportsItems>) {
        this.reportList.addAll(reportList)
        reportAdapter.notifyDataSetChanged()
    }

    private fun setEmptyReports() {
        setLoading(false)
        rv_report.visibility = View.GONE
        tv_empty.visibility = View.VISIBLE
    }

    private fun updateFeed() {
        setLoading(true)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getInitialPage()
        }
    }

    private fun setLoading(loading: Boolean) {
        loadingViews.visibility = if (loading) View.VISIBLE else View.GONE
    }

}