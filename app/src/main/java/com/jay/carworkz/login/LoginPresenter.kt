package com.jay.carworkz.login

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import com.jay.carworkz.model.AccessToken
import com.jay.carworkz.network.ApiInterface
import com.jay.carworkz.base.BasePresenter
import com.jay.carworkz.utility.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter : BasePresenter<LoginView> {

    private var loginView: LoginView? = null

    override fun onAttach(view: LoginView) {
        loginView = view
    }

    override fun onDetach() {
        loginView = null
    }

    fun checkIfAlreadyLoggedIn() {
        val token = loginView?.getAccessToken()
        if (token != null) {
            loginView?.startUserActivity()
        }
        loginView?.toggleProgressBarVisibility(View.GONE)
        loginView?.toggleSignInButtonVisibility(View.VISIBLE)
    }

    fun handleSignInWithGithubButtonClick() {
        loginView?.toggleProgressBarVisibility(View.VISIBLE)
        loginView?.toggleSignInButtonVisibility(View.GONE)
        loginView?.startBrowserActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/login/oauth/authorize?client_id=" + Constants.CLIENT_ID + "&scope=user&redirect_uri=" + Constants.CALLBACK)))
    }

    fun getAccessToken(intent: Intent?) {
        loginView?.toggleProgressBarVisibility(View.VISIBLE)
        loginView?.toggleSignInButtonVisibility(View.GONE)
        val uri = intent?.data
        if (uri != null && uri.toString().startsWith(Constants.CALLBACK)) {
            fetchAccessToken(uri)
        } else {
            loginView?.toggleProgressBarVisibility(View.GONE)
            loginView?.toggleSignInButtonVisibility(View.VISIBLE)
        }
    }

    private fun fetchAccessToken(uri: Uri) {
        val apiService = ApiInterface.create()
        val accessTokenCall = apiService.getAccessToken(Constants.OAUTH_URL, Constants.CLIENT_ID, Constants.CLIENT_SECRET, uri.getQueryParameter("code"))
        accessTokenCall.enqueue(object : Callback<AccessToken> {
            override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                loginView?.toggleProgressBarVisibility(View.GONE)
                loginView?.toggleSignInButtonVisibility(View.VISIBLE)
            }

            override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {
                if (response.isSuccessful) {
                    loginView?.saveAccessToken(response.body()?.accessToken)
                    loginView?.startUserActivity()
                } else {
                    loginView?.showToast("Something went wrong!", Toast.LENGTH_SHORT)
                    loginView?.toggleProgressBarVisibility(View.GONE)
                    loginView?.toggleSignInButtonVisibility(View.VISIBLE)
                }
            }

        })
    }
}