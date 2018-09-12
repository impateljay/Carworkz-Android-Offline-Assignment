package com.jay.carworkz.login

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jay.carworkz.R
import com.jay.carworkz.user.UserActivity
import com.jay.carworkz.utility.Constants
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPresenter = LoginPresenter()
        onAttach()

        sign_in_with_github.setOnClickListener {
            loginPresenter.handleSignInWithGithubButtonClick()
        }
    }

    override fun onResume() {
        super.onResume()
        loginPresenter.checkIfAlreadyLoggedIn()
        loginPresenter.getAccessToken(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetach()
    }

    override fun startBrowserActivity(intent: Intent) {
        startActivity(intent)
    }

    override fun startUserActivity() {
        startActivity(Intent(this@LoginActivity, UserActivity::class.java))
        finish()
    }

    override fun saveAccessToken(token: String?) {
        val prefs = this.getSharedPreferences(Constants.ACCESS_TOKEN_PREFS_FILENAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(Constants.ACCESS_TOKEN_KEY, token)
        editor.apply()
    }

    override fun getAccessToken(): String? {
        val prefs = this.getSharedPreferences(Constants.ACCESS_TOKEN_PREFS_FILENAME, Context.MODE_PRIVATE)
        return prefs?.getString(Constants.ACCESS_TOKEN_KEY, null)
    }

    override fun showToast(message: String?, length: Int) {
        Toast.makeText(this@LoginActivity, message, length).show()
    }

    override fun onAttach() {
        loginPresenter.onAttach(this)
    }

    override fun onDetach() {
        loginPresenter.onDetach()
    }

    override fun toggleProgressBarVisibility(visibility: Int) {
        progress_bar.visibility = visibility
    }

    override fun toggleSignInButtonVisibility(visibility: Int) {
        sign_in_with_github.visibility = visibility
    }
}
