package kz.smartideagroup.partner.content.view.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_cash_box.*
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.common.views.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class CashBoxFragment : BaseFragment() {

    private val bundle = Bundle()

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
        initListeners()
        initToolbar()

    }

    private fun setNavigation() {
        when (tv_count.text.isNotEmpty()) {
            true -> {
                when (tv_count.text.toString().toLong() >= Constants.MIN_SUM) {
                    true -> {
                        val sum = tv_count.text.toString().toLong()
                        bundle.putLong(Constants.TRANSACTION_SUM, sum)
                        view?.let {
                            Navigation.findNavController(it)
                                .navigate(R.id.payFragment, bundle)
                        }
                    }
                    false -> Snackbar.make(
                        requireView(),
                        getString(R.string.the_minimum_transaction_amount_for_one_purchase_is),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
            false ->
                Snackbar.make(
                    requireView(),
                    getString(R.string.enter_the_amount),
                    Snackbar.LENGTH_LONG
                ).show()
        }

    }

    private fun initListeners() {
        setOnClickNumber(btn_one, Constants.ONE_STRING, false)
        setOnClickNumber(btn_two, Constants.TWO_STRING, false)
        setOnClickNumber(btn_three, Constants.THREE_STRING, false)
        setOnClickNumber(btn_four, Constants.FOUR_STRING, false)
        setOnClickNumber(btn_five, Constants.FIVE_STRING, false)
        setOnClickNumber(btn_six, Constants.SIX_STRING, false)
        setOnClickNumber(btn_seven, Constants.SEVEN_STRING, false)
        setOnClickNumber(btn_eight, Constants.EIGHT_STRING, false)
        setOnClickNumber(btn_nine, Constants.NINE_STRING, false)
        setOnClickNumber(btn_zero, Constants.ZERO_STRING, true)

        btn_clear.onClick {
            when (tv_count.text.toString().isNotEmpty()) {
                true -> {
                    val clear =
                        tv_count.text.toString().substring(
                            Constants.ZERO,
                            tv_count.text.toString().length.minus(Constants.ONE)
                        )
                    tv_count.text = clear
                }
            }
        }

        btn_accept_payment.onClick {
            setNavigation()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setOnClickNumber(materialButton: MaterialButton, number: String, isZero: Boolean) {
        materialButton.setOnClickListener(View.OnClickListener {
            when (isZero) {
                true -> {
                    when (tv_count.text.toString().isNotEmpty()) {
                        true -> {
                            tv_count.text = tv_count.text.toString().plus(number)
                        }
                    }
                }
                false -> tv_count.text = tv_count.text.toString().plus(number)
            }

        })

    }

    private fun initToolbar() {
        toolbarCashBox.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbarCashBox.setTitleTextColor(Color.WHITE)
        toolbarCashBox.setNavigationOnClickListener {
            view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.homeFragment) }
        }
    }

}