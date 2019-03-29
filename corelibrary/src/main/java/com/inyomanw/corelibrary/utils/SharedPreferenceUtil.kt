package com.inyomanw.corelibrary.utils

import android.content.SharedPreferences

/**
 * [SharedPreferenceUtil] contain sharedPreference operation
 * @author voen
 */
class SharedPreferenceUtil(val sharedPreference: SharedPreferences) {

    object NetworkKeys {
        const val NETWORK_HEADER_TOKEN = "header_token"
        const val FCM_TOKEN = "fcm_token"
    }

    /**
     * save boolean value into preference
     * @param key token key
     * @param value boolean value to be saved
     */
    fun setBoolean(key: String, value: Boolean) {
        sharedPreference.edit().putBoolean(key, value).apply()
    }

    /**
     * save String value into preference
     * @param key token key
     * @param value String value to be saved
     */
    fun setString(key: String, value: String) {
        sharedPreference.edit().putString(key, value).apply()
    }

    /**
     * save Int value into preference
     * @param key token key
     * @param value Int value to be saved
     */
    fun setInt(key: String, value: Int) {
        sharedPreference.edit().putInt(key, value).apply()
    }

    /**
     * save Long value into preference
     * @param key token key
     * @param value Long value to be saved
     */
    fun setLong(key: String, value: Long) {
        sharedPreference.edit().putLong(key, value).apply()
    }

    /**
     * Load boolean value from preference
     * @param key token key
     * @return [Boolean] default value false
     */
    fun getBoolean(key: String) = sharedPreference.getBoolean(key, false)

    /**
     * Load String value from preference
     * @param key token key
     * @return [String] default empty string
     */
    fun getString(key: String) = sharedPreference.getString(key, "")

    /**
     * Load Int value from preference
     * @param key token key
     * @return [Int] default value 0
     */
    fun getInt(key: String) = sharedPreference.getInt(key, 0)

    /**
     * Load Long value from preference
     * @param key token key
     * @return [Long] default value 0
     */
    fun getLong(key: String) = sharedPreference.getLong(key, 0)

    /**
     * remove all saved value on preference
     */
    fun clear() {
        sharedPreference.edit().clear().apply()
    }
}