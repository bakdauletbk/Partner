package kz.smartideagroup.partner.content.view

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_foundation.*
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.views.BaseActivity

class FoundationActivity : BaseActivity() {

    private var exit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foundation)
        lets()
    }

    private fun lets() {
        val navController = findNavController(R.id.fragment)
        setupNavController(navController)
    }

    private fun setupNavController(navController: NavController) {
        bottom_navigation.itemIconTintList = null
        bottom_navigation.setupWithNavController(navController)
    }

//    override fun onBackPressed() {
//        if (exit) {
//            val intent = Intent(Intent.ACTION_MAIN)
//            intent.addCategory(Intent.CATEGORY_HOME)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
//        } else {
//            Toast.makeText(
//                this, "Для выхода снова нажмите «Назад».",
//                Toast.LENGTH_SHORT
//            ).show()
//            exit = true
//        }
//    }

}