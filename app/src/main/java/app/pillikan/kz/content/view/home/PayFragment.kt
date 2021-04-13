package app.pillikan.kz.content.view.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_pay.*
import app.pillikan.kz.R
import app.pillikan.kz.common.remote.Constants
import app.pillikan.kz.common.views.BaseFragment
import app.pillikan.kz.content.viewmodel.home.PayViewModel


class PayFragment : BaseFragment() {

    companion object {
        const val TAG = "PayFragment"
    }

    private var currentBright: Float? = null

    private lateinit var viewModel: PayViewModel

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
        initViewModel()
        initToolbar()
        getOrderId()
        initObservers()
    }

    private fun initObservers() {
        viewModel.orderId.observe(viewLifecycleOwner, {
            if (it != null) {
                setLoading(false)
                initQr(it)
            } else {
                setLoading(false)
                Log.d(TAG, "orderId null")
            }
        })
    }

    private fun getOrderId() {
        setLoading(true)
        viewModel.getOrderId()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(PayViewModel::class.java)
    }

    private fun initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationOnClickListener {
            view?.let { it1 ->
                Navigation.findNavController(it1).navigate(R.id.cashBoxFragment)
            }
        }
    }

    private fun initQr(orderId: String) {
        val sum = arguments?.getLong(Constants.TRANSACTION_SUM)
        tv_price_pay.text = sum.toString()
        val qrGenerationString = "$orderId / $sum"
        screenBrightness(1f)
        val qrgEncoder = QRGEncoder(qrGenerationString, null, QRGContents.Type.TEXT, 800)
        val qrBit = qrgEncoder.bitmap
        iv_qr.setImageBitmap(qrBit)
    }

    private fun screenBrightness(lvl: Float) {
        val layout = activity?.window?.attributes
        currentBright = layout?.screenBrightness
        layout?.screenBrightness = lvl
        activity?.window?.attributes = layout
    }

    private fun setLoading(loading: Boolean) {
        loadingViews.visibility = if (loading) View.VISIBLE else View.GONE
    }

}