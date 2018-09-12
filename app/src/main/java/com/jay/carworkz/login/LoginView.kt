package com.jay.carworkz.login

import android.content.Intent
import android.opengl.Visibility
import com.jay.carworkz.base.BaseView

interface LoginView : BaseView {
    fun showToast(message: String?, length: Int)
    fun startBrowserActivity(intent: Intent)
    fun startUserActivity()
    fun saveAccessToken(token: String?)
    fun getAccessToken(): String?
    fun toggleProgressBarVisibility(visibility: Int)
    fun toggleSignInButtonVisibility(visibility: Int)
}