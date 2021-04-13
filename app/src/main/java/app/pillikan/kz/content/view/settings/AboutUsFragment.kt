package app.pillikan.kz.content.view.settings

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_about_us.*
import app.pillikan.kz.BuildConfig
import app.pillikan.kz.R
import app.pillikan.kz.common.helpers.call
import app.pillikan.kz.common.helpers.openLink
import app.pillikan.kz.common.remote.Constants
import app.pillikan.kz.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class AboutUsFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.settingsFragment) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        initToolbar()
        setVersionName()
        initListeners()
    }

    private fun setVersionName() {
        tv_version.text = BuildConfig.VERSION_NAME
    }

    private fun initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationOnClickListener {
            view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.settingsFragment) }
        }
    }

    private fun initListeners() {
        tv_number_call.onClick {
            activity?.let { call(Constants.PHONE_NUMBER, requireContext(), it) }
        }
        iv_instagram.onClick {
            activity?.let { openLink(Constants.INSTAGRAM_LINK, it) }
        }
        iv_youtube.onClick {
            activity?.let { openLink(Constants.YOUTUBE_LINK, it) }
        }
        btn_faq.onClick {
            activity?.let { openLink(Constants.FAQ_LINK, it) }
        }
    }

}