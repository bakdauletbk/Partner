package app.pillikan.kz.content.view.accept_order

import android.annotation.SuppressLint
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_success.*
import app.pillikan.kz.R
import app.pillikan.kz.common.views.BaseActivity
import app.pillikan.kz.content.view.FoundationActivity
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