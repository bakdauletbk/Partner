package kz.smartideagroup.partner.content.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.views.BaseFragment
import kz.smartideagroup.partner.content.viewmodel.home.HomeViewModel
import org.jetbrains.anko.sdk27.coroutines.onClick

class HomeFragment : BaseFragment() {

    private lateinit var viewModel: HomeViewModel

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initVewModel()
        initListeners()
        updateFeed()
        initObservers()
    }

    private fun initListeners() {
        rl_cash_box.onClick {
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_homeFragment_to_cashBoxFragment)
            }
        }
        rl_delivery.onClick {
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.deliveryFragment)
            }
        }
        rl_reports.onClick {
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.reportsFragment)
            }
        }
        rl_settings.onClick {
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.settingsFragment)
            }
        }
    }

    private fun initObservers() {
        viewModel.isNetworkConnection.observe(viewLifecycleOwner, {
            when (it) {
                false -> errorDialog(getString(R.string.error_unknown_body))
            }
        })
        viewModel.retailName.observe(viewLifecycleOwner, {
            toolbar.title = it
        })
    }

    private fun updateFeed() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.checkNetwork(requireContext())
        }
        viewModel.getRetailName()
    }

    private fun initVewModel() {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

}