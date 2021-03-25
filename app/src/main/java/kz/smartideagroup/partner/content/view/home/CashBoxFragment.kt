package kz.smartideagroup.partner.content.view.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_about_us.*
import kotlinx.android.synthetic.main.fragment_cash_box.*
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.views.BaseFragment

class CashBoxFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.homeFragment)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cash_box, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initViewModel()
        initToolbar()
    }

    private fun initToolbar() {
        toolbarCashBox.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbarCashBox.setTitleTextColor(Color.WHITE)
        toolbarCashBox.setNavigationOnClickListener {
            view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.homeFragment) }
        }
    }

    private fun initViewModel() {

    }

}