package kz.smartideagroup.partner.content.view.accept_order

import android.app.KeyguardManager
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.navigation.findNavController
import kz.smartideagroup.partner.R
import kz.smartideagroup.partner.common.remote.Constants
import kz.smartideagroup.partner.common.views.BaseActivity

class AcceptOrderActivity : BaseActivity() {

    private var mediaPlayer: MediaPlayer? = null

    private val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accept_order)
        lets()
    }

    private fun lets() {
        initCheckPermission()
        initNavigation()
        setStartMediaPlayer()
    }

    private fun setStartMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.waiting)
        mediaPlayer!!.isLooping = true
        mediaPlayer!!.start()
    }

    private fun initCheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            keyguardManager.requestDismissKeyguard(this, null)
        } else {
            this.window.addFlags(
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }
    }

    private fun initNavigation() {
        val orderId = intent.getIntExtra(Constants.ACCEPT_ORDER_ID, 0)

        bundle.putInt(Constants.ACCEPT_ORDER_ID, orderId)

        findNavController(R.id.fragment2)
            .navigate(R.id.acceptOrderFragment, bundle)

    }

    override fun onDestroy() {
        mediaPlayer!!.release()
        super.onDestroy()
    }

    override fun onPause() {
        mediaPlayer!!.pause()
        super.onPause()
    }

    override fun onStop() {
        mediaPlayer!!.stop()
        super.onStop()
    }

    override fun onResume() {
        if (!mediaPlayer!!.isPlaying) {
            mediaPlayer!!.start()
        }
        super.onResume()
    }

    override fun onBackPressed() {}

}