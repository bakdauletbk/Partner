package kz.smartideagroup.partner.authorization.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_sigin.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.authorization.model.request.LoginBodyRequest
import kz.smartideagroup.partner.authorization.viewmodel.AuthorizationViewModel
import kz.smartideagroup.partner.common.views.BaseActivity
import kz.smartideagroup.partner.content.view.FoundationActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick

class AuthorizationActivity : BaseActivity() {

    private lateinit var viewModel: AuthorizationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sigin)
        lets()
    }

    private fun lets() {
        initViewModel()
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        viewModel.isError.observe(this, {
            errorDialog(getString(R.string.error_unknown_body))
        })

        viewModel.isNetworkConnection.observe(this, {
            when (it) {
                true -> prepareLogin()
                false -> errorDialog(getString(R.string.error_unknown_body))
            }
        })

        viewModel.isSuccess.observe(this, {
            when (it) {
                true -> {
                    setLoading(false)
                    finish()
                    startActivity(intentFor<FoundationActivity>())
                }
                false -> onSuccessFullDialog()
            }
        })
    }

    private fun initListeners() {
        btn_sign_in.onClick {
            checkNetwork()
        }
    }

    private fun checkNetwork() {
        setLoading(true)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.checkNetwork(this@AuthorizationActivity)
        }
    }

    private fun prepareLogin() {
        val login = et_login.text.toString()
        val password = et_password.text.toString()

        val loginBody = LoginBodyRequest(password = password, userName = login)

        when (login.isNotEmpty() && password.isNotEmpty()) {
            true -> sigIn(loginBody)
            false -> Toast.makeText(
                this,
                "Введенные вами данные некорректны!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun sigIn(loginBodyRequest: LoginBodyRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.signIn(loginBodyRequest)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AuthorizationViewModel::class.java)
    }

    private fun onSuccessFullDialog() {
        alert {
            title = getString(R.string.error_auth_wrong_data_title)
            message = getString(R.string.error_auth_wrong_data_msg)
            isCancelable = false
            positiveButton(getString(R.string.dialog_retry)) { dialog ->
                setLoading(false)
                dialog.dismiss()
            }
            negativeButton(getString(R.string.dialog_exit)) {
                finish()
            }
        }.show()
    }

    private fun setLoading(loading: Boolean) {
        loadingView.visibility = if (loading) View.VISIBLE else View.GONE
        btn_sign_in.isEnabled = !loading
        et_login.isEnabled = !loading
        et_password.isEnabled = !loading
    }

}