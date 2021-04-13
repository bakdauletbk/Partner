package app.pillikan.kz.content.view.reports

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.CalendarView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.addCallback
import androidx.navigation.Navigation
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_reports.*
import app.pillikan.kz.R
import app.pillikan.kz.common.remote.Constants
import app.pillikan.kz.common.views.BaseFragment
import app.pillikan.kz.content.view.reports.adapter.PagerAdapter
import org.jetbrains.anko.sdk27.coroutines.onClick

class ReportsFragment : BaseFragment() {

    private var alertDialog: Dialog? = null
    private var calendarView: CalendarView? = null
    private var dayFrom = Constants.ZERO
    private var monthFrom = Constants.ZERO
    private var yearFrom = Constants.ZERO
    private var dayTo = Constants.ZERO
    private var monthTo = Constants.ZERO
    private var yearTo = Constants.ZERO

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
        return inflater.inflate(R.layout.fragment_reports, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initListeners()
        initCalendarView()
        initViewPager()
    }

    private fun initListeners() {
        btn_from_date.setOnClickListener {
            calendarAlertDialog(true)
        }
        btn_to.setOnClickListener {
            calendarAlertDialog(false)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initCalendarView() {
        btn_from_date.text =
            Constants.dayFrom.toString() + Constants.DOT + Constants.monthFrom.toString() + Constants.DOT + Constants.yearFrom
        btn_to.text =
            Constants.dayTo.toString() + Constants.DOT + Constants.monthTo.toString() + Constants.DOT + Constants.yearTo
    }

    private fun initViewPager() {

        tab_layout!!.addTab(tab_layout!!.newTab().setText(Constants.ALL_TAB))
        tab_layout!!.addTab(tab_layout!!.newTab().setText(Constants.PAY_TAB))
        tab_layout!!.addTab(tab_layout!!.newTab().setText(Constants.DELIVERY_TAB))
        tab_layout!!.tabGravity = TabLayout.GRAVITY_FILL
        tab_layout.setTabWidthAsWrapContents(Constants.ZERO, false)
        tab_layout.setTabWidthAsWrapContents(Constants.ONE, false)
        tab_layout.setTabWidthAsWrapContents(Constants.TWO, true)

        val adapter = PagerAdapter(childFragmentManager, tab_layout!!.tabCount)
        vp_report!!.adapter = adapter

        vp_report!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                vp_report!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun TabLayout.setTabWidthAsWrapContents(tabPosition: Int, select: Boolean) {
        val layout =
            (this.getChildAt(Constants.ZERO) as LinearLayout).getChildAt(tabPosition) as LinearLayout
        val layoutParams = layout.layoutParams as LinearLayout.LayoutParams
        when (select) {
            true -> layoutParams.weight = Constants.TWO_FLOAT
            false -> layoutParams.weight = Constants.ONE_FLOAT
        }
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        layout.layoutParams = layoutParams
    }

    @SuppressLint("SetTextI18n")
    private fun calendarAlertDialog(isStart: Boolean) {
        alertDialog = Dialog(requireContext())
        alertDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog!!.setContentView(R.layout.ui_calendar_alert_dialog)

        calendarView = alertDialog!!.findViewById(R.id.calendar_view) as CalendarView

        val tvCancel = alertDialog!!.findViewById(R.id.tv_cancel) as TextView
        val tvOk = alertDialog!!.findViewById(R.id.tv_ok) as TextView

        calendarView!!.setOnDateChangeListener(CalendarView.OnDateChangeListener { _, year, month, dayOfMonth ->
            when (isStart) {
                true -> {
                    dayFrom = dayOfMonth
                    monthFrom = month.plus(Constants.ONE)
                    yearFrom = year
                }
                false -> {
                    dayTo = dayOfMonth
                    monthTo = month.plus(Constants.ONE)
                    yearTo = year
                }
            }
        })

        tvCancel.onClick {
            alertDialog!!.dismiss()
        }

        tvOk.onClick {
            when (isStart) {
                true -> {
                    if (yearFrom != Constants.ZERO) {
                        btn_from_date.text = "$dayFrom.$monthFrom.$yearFrom"
                        Constants.dayFrom = dayFrom
                        Constants.monthFrom = monthFrom
                        Constants.yearFrom = yearFrom
                    }
                }
                false -> {
                    if (yearTo != Constants.ZERO) {
                        btn_to.text = "$dayTo.$monthTo.$yearTo"
                        Constants.dayTo = dayTo
                        Constants.monthTo = monthTo
                        Constants.yearTo = yearTo
                    }
                }
            }
            alertDialog!!.dismiss()
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.reportsFragment)
            }


        }
        alertDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog!!.show()
    }
}