package app.pillikan.kz.content.view.reports

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
import app.pillikan.kz.R
import app.pillikan.kz.common.remote.Constants
import app.pillikan.kz.common.views.BaseFragment
import app.pillikan.kz.common.views.PaginationCompletedScrollListener
import app.pillikan.kz.content.model.response.reports.ReportsItems
import app.pillikan.kz.content.view.reports.adapter.ReportsAdapter
import app.pillikan.kz.content.viewmodel.reports.DeliveryReportsViewModel

class DeliveryReportsFragment : BaseFragment() {

    private var reportList: ArrayList<ReportsItems> = arrayListOf()
    private val reportAdapter by lazy { ReportsAdapter(reportList) }
    private lateinit var viewModel: DeliveryReportsViewModel
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

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(DeliveryReportsViewModel::class.java)
    }

    private fun updateFeed() {
        setLoading(true)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getInitialPage()
        }
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

    private fun setLoading(loading: Boolean) {
        loadingViews.visibility = if (loading) View.VISIBLE else View.GONE
    }

}