package kz.smartideagroup.partner.content.view.delivery

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_menu.*
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.views.BaseFragment

class MenuFragment : BaseFragment() {

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
        initToolbar()
        initListeners()
    }

    private fun initToolbar() {
        toolbarMenu.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbarMenu.setTitleTextColor(Color.WHITE)
        toolbarMenu.setNavigationOnClickListener {
            view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.deliveryFragment) }
        }
    }

    private fun initListeners() {

    }

    private fun initViewModel() {

    }
}