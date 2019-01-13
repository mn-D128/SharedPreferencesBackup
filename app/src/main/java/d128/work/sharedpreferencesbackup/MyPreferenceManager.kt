package d128.work.sharedpreferencesbackup

import android.content.Context
import android.content.SharedPreferences

object MyPreferenceManager {

    val NAME = "PREF"
    private val KEY = "KEY"

    private val NAME_NOBACKUP = "NAME_NOBACKUP"
    private val KEY_CAN_RESTORE = "KEY_CAN_RESTORE"

    private fun getPreference(context: Context, name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    fun getValue(context: Context): Int {
        val pref = getPreference(context, NAME)
        return pref.getInt(KEY, 0)
    }

    fun setValue(context: Context, value: Int) {
        val pref = getPreference(context, NAME)
        val editor = pref.edit()
        editor.putInt(KEY, value)
        editor.commit()
    }

    fun canRestore(context: Context): Boolean {
        val pref = getPreference(context, NAME_NOBACKUP)
        return pref.getBoolean(KEY_CAN_RESTORE, true)
    }

    fun setRestore(context: Context, canRestore: Boolean) {
        val pref = getPreference(context, NAME_NOBACKUP)
        val editor = pref.edit()
        editor.putBoolean(KEY_CAN_RESTORE, canRestore)
        editor.commit()
    }

}