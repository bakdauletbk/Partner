package app.pillikan.kz.splash

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import app.pillikan.kz.R
import app.pillikan.kz.authorization.view.AuthorizationActivity
import app.pillikan.kz.common.views.BaseActivity
import app.pillikan.kz.content.view.FoundationActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor

class SplashActivity : BaseActivity() {

    private lateinit var viewModel: SplashViewModel
    private var isFirstLaunch = true
    private val mContext = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        lets()
    }

    private fun lets() {
        initViewModel()
        observer()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            checkNetwork()
        }
    }

    private suspend fun checkNetwork() {
        if (isFirstLaunch) {
            delay(2000L)
            isFirstLaunch = false
        }
        viewModel.checkNetwork(mContext)
    }

    private fun observer() {
        viewModel.isNetworkConnection.observe(mContext, {
            if (it) {
                checkAuthorize()
            } else {
                showErrorDialog(
                    getString(R.string.error_no_internet_title),
                    getString(R.string.error_no_internet_msg)
                )
            }
        })

        viewModel.isAuthorize.observe(mContext, {
            if (it) {
                startActivity(intentFor<FoundationActivity>())
                finish()
            } else {
                startActivity(intentFor<AuthorizationActivity>())
                finish()
            }
        })

        viewModel.isError.observe(mContext, {
            if (it) {
                showErrorDialog(
                    getString(R.string.error_unknown_title),
                    getString(R.string.error_unknown_body)
                )
            }
        })

    }

    private fun checkAuthorize() {
        viewModel.checkAuthorize()
    }

    private fun showErrorDialog(errorTitle: String, errorMessage: String) {
        alert {
            title = errorTitle
            message = errorMessage
            isCancelable = false
            negativeButton(getString(R.string.dialog_exit)) {
                finish()
            }
            positiveButton(getString(R.string.dialog_retry)) {
                it.dismiss()
                onRestart()
            }
        }.show()
    }

    override fun onRestart() {
        super.onRestart()
        CoroutineScope(Dispatchers.IO).launch {
            checkNetwork()
        }
    }

}