package com.inyomanw.corelibrary.deps

import android.content.Context
import com.inyomanw.corelibrary.utils.SharedPreferenceUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilModule(private val sharedPreferenceName: String, private val context: Context){

    /**
     * @suppress
     */
    @Provides
    @Singleton
    @SuppressWarnings("unused")
    fun providesSharedPreferenceUtil(): SharedPreferenceUtil {
        val sp = context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)
        return SharedPreferenceUtil(sp)
    }
}