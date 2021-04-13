package app.pillikan.kz.content.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_settings.*
import app.pillikan.kz.R
import app.pillikan.kz.authorization.view.AuthorizationActivity
import app.pillikan.kz.common.helpers.openLink
import app.pillikan.kz.common.remote.Constants
import app.pillikan.kz.common.views.BaseFragment
import app.pillikan.kz.content.viewmodel.settings.SettingsViewModel
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.intentFor

class SettingsFragment : BaseFragment() {

    private lateinit var viewModel: SettingsViewModel

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
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initViewModel()
        initListeners()
        setRetailName()
        initObservers()
    }

    private fun setRetailName() {
        viewModel.getRetailName()
    }

    private fun initObservers() {
        viewModel.retailName.observe(viewLifecycleOwner, {
            tv_retail_name.text = it
        })
        viewModel.logout.observe(viewLifecycleOwner, {
            when (it) {
                true -> startActivity(intentFor<AuthorizationActivity>())
                false -> Toast.makeText(
                    context,
                    getString(R.string.error_while_logging_out_of_profile),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun initListeners() {
        ll_about_us.onClick {
            view?.let {
                Navigation.findNavController(it)
                    .navigate(R.id.action_settingsFragment_to_aboutUsFragment)
            }
        }
        ll_faq.onClick {
            //to do FAQ
        }
        btn_logout.onClick {
            viewModel.logout()
        }
        ll_faq.onClick {
            activity?.let { openLink(Constants.FAQ_LINK, it) }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
    }

}