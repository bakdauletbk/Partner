package kz.smartideagroup.partner.content.view

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_foundation.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.authorization.view.AuthorizationActivity
import kz.smartideagroup.partner.common.views.BaseActivity
import kz.smartideagroup.partner.content.model.request.home.SaveFirebaseTokenRequest
import kz.smartideagroup.partner.content.viewmodel.FoundationViewModel
import org.jetbrains.anko.intentFor

class FoundationActivity : BaseActivity() {

    private lateinit var viewModel: FoundationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foundation)
        lets()
    }

    private fun lets() {
        initViewModel()
        initNavController()
        getIsAuthorize()
        initObservers()
    }

    private fun getIsAuthorize() {
        viewModel.getIsAuthorize()
    }

    private fun initObservers() {
        viewModel.isAuthorize.observe(this, {
            when (it) {
                true -> getFirebaseToken()
                false -> {
                    startActivity(intentFor<AuthorizationActivity>())
                    finish()
                }
            }
        })
        viewModel.isSendFToken.observe(this, {
            when (it) {
                true -> {
                }
                false -> {
                }
            }
        })
    }

    private fun getFirebaseToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                val token = task.result!!.token
                val saveFirebaseTokenRequest = SaveFirebaseTokenRequest(token)
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.sendFireBaseToken(saveFirebaseTokenRequest)
                }
            })
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(FoundationViewModel::class.java)
    }

    private fun initNavController() {
        val navController = findNavController(R.id.fragment)
        setupNavController(navController)
    }

    private fun setupNavController(navController: NavController) {
        bottom_navigation.itemIconTintList = null
        bottom_navigation.setupWithNavController(navController)

    }

}