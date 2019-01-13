package d128.work.sharedpreferencesbackup

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.backup.BackupManager
import android.app.backup.RestoreObserver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : Activity() {

    private var backupMgr: BackupManager? = null
    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.text_view)

        findViewById<Button>(R.id.button).setOnClickListener(clickListener)

        backupMgr = BackupManager(applicationContext)
    }

    override fun onResume() {
        super.onResume()

        updateView()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P && MyPreferenceManager.canRestore(applicationContext)) {
            @Suppress("DEPRECATION")
            backupMgr?.requestRestore(restoreObserver)
        }
    }

    private fun updateView() {
        textView?.let {
            it.text = Integer.toString(MyPreferenceManager.getValue(applicationContext))
        }
    }

    private fun reboot() {
        val context = applicationContext
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        val alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmMgr.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent)

        Process.killProcess(Process.myPid())
    }

    private val clickListener: (View) -> Unit = {
        val context = applicationContext

        var value = MyPreferenceManager.getValue(context)
        value++
        MyPreferenceManager.setValue(context, value)

        BackupManager.dataChanged(context.packageName)

        updateView()
    }

    private val restoreObserver = object: RestoreObserver() {

        override fun restoreStarting(numPackages: Int) {

        }

        override fun onUpdate(nowBeingRestored: Int, currentPackage: String) {

        }

        override fun restoreFinished(error: Int) {
            //updateView()
            MyPreferenceManager.setRestore(applicationContext, false)
            reboot()
        }

    }

}
