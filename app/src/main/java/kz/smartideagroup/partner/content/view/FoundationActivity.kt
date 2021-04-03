package kz.smartideagroup.partner.content.view

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.Window
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.button.MaterialButton
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_foundation.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.authorization.view.AuthorizationActivity
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.common.views.BaseActivity
import kz.smartideagroup.partner.content.model.request.home.SaveFirebaseTokenRequest
import kz.smartideagroup.partner.content.viewmodel.FoundationViewModel
import org.jetbrains.anko.intentFor


class FoundationActivity : BaseActivity() {

    private lateinit var viewModel: FoundationViewModel

    private var alertDialog: Dialog? = null

    private var currentNavigationItemId = 0

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

    override fun onResume() {
        when (intent.getIntExtra(Constants.INTENT_NAV, Constants.ZERO) == Constants.ONE) {
            true -> setNavigateDelivery()
        }
        if (Build.VERSION.SDK_INT >= Constants.SDK_24) {
            if (!Settings.canDrawOverlays(this)) {
                showOverlayPermissionAlert()
            }
        }
        super.onResume()
    }

    private fun showOverlayPermissionAlert() {
        alertDialog = Dialog(this)
        alertDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog!!.setCancelable(false)
        alertDialog!!.setContentView(R.layout.dialog_overlay)
        val button: MaterialButton = alertDialog!!.findViewById(R.id.btn_go_settings)
        button.setOnClickListener(View.OnClickListener {
            try {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse(Constants.PACKAGE + this.packageName)
                )

                startActivityForResult(intent, Constants.REQUEST_CODE)
            } catch (ex: Exception) {
            }
            alertDialog!!.dismiss()
        })
        alertDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog!!.show()
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
        showHomeFragment()
        bottom_navigation.itemIconTintList = null
        bottom_navigation.setupWithNavController(navController)
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            if (item.itemId != currentNavigationItemId) {
                when (item.itemId) {
                    R.id.menu_homeFragment -> showHomeFragment()
                    R.id.menu_deliveryFragment -> showDeliveryFragment()
                    R.id.menu_reportsFragment -> showReportsFragment()
                    R.id.menu_notificationFragment -> showNotificationFragment()
                    R.id.menu_settingsFragment -> showSettingsFragment()
                }
            }
            true
        }

    }

    fun setNavigateDelivery() {
        bottom_navigation.selectedItemId = R.id.menu_deliveryFragment
    }

    fun setReportsDelivery() {
        bottom_navigation.selectedItemId = R.id.menu_reportsFragment
    }

    fun setSettingsDelivery() {
        bottom_navigation.selectedItemId = R.id.menu_settingsFragment
    }

    private fun showHomeFragment() {
        currentNavigationItemId = R.id.menu_homeFragment
        findNavController(R.id.fragment)
            .navigate(R.id.homeFragment)
    }

    private fun showSettingsFragment() {
        currentNavigationItemId = R.id.menu_settingsFragment
        findNavController(R.id.fragment)
            .navigate(R.id.settingsFragment)
    }

    private fun showNotificationFragment() {
        currentNavigationItemId = R.id.menu_notificationFragment
        findNavController(R.id.fragment)
            .navigate(R.id.notificationFragment)
    }

    private fun showReportsFragment() {
        currentNavigationItemId = R.id.menu_reportsFragment
        findNavController(R.id.fragment)
            .navigate(R.id.reportsFragment)
    }

    private fun showDeliveryFragment() {
        currentNavigationItemId = R.id.menu_deliveryFragment
        findNavController(R.id.fragment)
            .navigate(R.id.deliveryFragment)
    }

}