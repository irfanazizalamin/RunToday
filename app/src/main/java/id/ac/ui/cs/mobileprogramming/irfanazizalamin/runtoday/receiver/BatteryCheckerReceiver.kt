package id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.R


class BatteryCheckerReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val level = intent?.getIntExtra("level", 0) ?: return
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val mVibratePattern = longArrayOf(0, 200, 400, 200, 600, 200, 100)

        if (level === 10) {
            Toast.makeText(context, R.string.low_battrey_info, Toast.LENGTH_LONG).show()
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(
                    VibrationEffect.createWaveform(mVibratePattern, -1)
                )
            } else {
                vibrator.vibrate(200)
            }
        }
    }
}