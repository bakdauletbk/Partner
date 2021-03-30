package kz.smartideagroup.partner.content.view.accept_order

import android.annotation.SuppressLint
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_success.*
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.views.BaseActivity
import kz.smartideagroup.partner.content.view.FoundationActivity
import org.jetbrains.anko.intentFor

class SuccessPaymentActivity : BaseActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
        tv_username.text="от "+intent.getStringExtra("name")
        tv_phone.text="+"+intent.getStringExtra("phone")
        btnClose.setOnClickListener {
            startActivity(intentFor<FoundationActivity>())
            finish()
        }
    }

    override fun onBackPressed() {
        startActivity(intentFor<FoundationActivity>())
        finish()
    }
}