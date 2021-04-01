package kz.smartideagroup.partner.content.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.views.BaseFragment

class PayFragment:BaseFragment() {

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
        initQr()
    }

    private fun initQr() {

    }

    private fun initListeners() {

    }


}