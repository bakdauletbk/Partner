package kz.smartideagroup.partner.content.view.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_cash_box.*
import kotlinx.android.synthetic.main.fragment_cash_box.toolbarCashBox
import kotlinx.android.synthetic.main.fragment_pay.*
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.views.BaseFragment

class PayFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.cashBoxFragment)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pay, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initListeners()
        initToolbar()
        initQr()
    }

    private fun initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationOnClickListener {
            view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.cashBoxFragment) }
        }
    }

    private fun initQr() {

    }

    private fun initListeners() {

    }


}