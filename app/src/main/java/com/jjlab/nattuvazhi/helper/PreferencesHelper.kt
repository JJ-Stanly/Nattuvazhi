package com.jjlab.nattuvazhi.helper

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import java.lang.UnsupportedOperationException

object PreferencesHelper {

    fun defaultPreferences(context: Context): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)

    fun customPreferences(context: Context, name: String): SharedPreferences =
            context.getSharedPreferences(name, Context.MODE_PRIVATE)

    inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit({it.putString(key, value)})
            is Int -> edit({it.putInt(key, value)})
            is Boolean -> edit({it.putBoolean(key, value)})
            is Float -> edit({it.putFloat(key, value)})
            is Long -> edit({it.putLong(key, value)})
            else -> throw UnsupportedOperationException("Unsupported data type to save to Preferences!")
        }
    }

    operator inline fun <reified  T: Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Unknown data type attempted to be retrieved!")

        }
    }
}

/*
    import com.experiments.preferencehelper.PreferenceHelper.get

    import com.experiments.preferencehelper.PreferenceHelper.set

    class SampleClass {
        fun prefOps(context: Context) {
            val prefs = defaultPrefs(this)
            prefs[Consts.SharedPrefs.KEY] = "any_type_of_value" //setter
            val value: String? = prefs[Consts.SharedPrefs.KEY] //getter
            val anotherValue: Int? = prefs[Consts.SharedPrefs.KEY, 10] //getter with default value
        }
    }
*/
