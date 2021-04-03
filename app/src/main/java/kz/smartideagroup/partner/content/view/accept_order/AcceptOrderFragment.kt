package kz.smartideagroup.partner.content.view.accept_order

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_accept_order.*
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class AcceptOrderFragment : BaseFragment() {

    private val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_accept_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        setOrderInfo()
        initListeners()
    }

    private fun initListeners() {
        btn_accept_order.onClick {
            val orderId = arguments?.getInt(Constants.ACCEPT_ORDER_ID, Constants.ZERO)

            bundle.putInt(Constants.ACCEPT_ORDER_ID, orderId!!)

            view?.let { it1 ->
                Navigation.findNavController(it1)
                    .navigate(R.id.action_acceptOrderFragment_to_confirmOrderFragment, bundle)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setOrderInfo() {
        val orderId = arguments?.getInt(Constants.ACCEPT_ORDER_ID, Constants.ZERO)
        tv_order_id.text = Constants.ORDER + orderId
    }

}