package com.punzila.punzilateacherparent.utils

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    private var mPrefs: SharedPreferences? = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var hasCompleteWalkThrough: Boolean?
    get() = mPrefs?.getBoolean(ISFIRSTRUN, true)
    set(isfirstrun) = mPrefs?.edit()?.putBoolean(ISFIRSTRUN, isfirstrun!!)!!.apply()

    companion object {
        private const val PREFS_NAME = "my_prefs"
        private const val USERID = "userID"
        private const val USERNAME = "userName"
        private const val ISFIRSTRUN = "hasCompletedWalkthrough"
        private const val FULLNAME = "fullname"

        private var instance:Prefs? = null

        fun getInstance(context: Context): Prefs? {
            if (instance == null) {
                synchronized(Prefs::class.java) {
                    if (instance == null) {
                        instance = Prefs(context.applicationContext)
                    }
                }
            }
            return instance
        }
    }
}