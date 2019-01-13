package d128.work.sharedpreferencesbackup

import android.app.backup.BackupAgentHelper
import android.app.backup.SharedPreferencesBackupHelper

class MyBackupAgent: BackupAgentHelper() {

    companion object {
        private val BACKUP_KEY = "BACKUP_KEY"
    }

    override fun onCreate() {
        super.onCreate()

        SharedPreferencesBackupHelper(this, MyPreferenceManager.NAME).also {
            addHelper(BACKUP_KEY, it)
        }
    }

}