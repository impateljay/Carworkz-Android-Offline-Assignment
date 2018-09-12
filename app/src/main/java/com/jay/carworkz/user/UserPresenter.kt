package com.jay.carworkz.user

import android.content.Context
import android.view.View
import android.widget.Toast
import com.jay.carworkz.base.BasePresenter
import com.jay.carworkz.data.AppDatabase
import com.jay.carworkz.model.User
import com.jay.carworkz.network.ApiInterface
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class UserPresenter : BasePresenter<UserView> {

    private var userView: UserView? = null
    private var database: AppDatabase? = null

    override fun onAttach(view: UserView) {
        userView = view
    }

    override fun onDetach() {
        userView = null
    }

    fun getUsers(context: Context) {
        database = AppDatabase.getDatabaseInstance(context)
        doAsync {
            val usersCount = database?.userDao()?.getUsersCount()
            val updatedAt = userView?.getUpdatedAt()
            if (usersCount != null && usersCount > 0 && updatedAt != null && ((Date().time - updatedAt) / 60000) < 60) {
                val users = database?.userDao()?.getUsers()
                uiThread {
                    userView?.addUsers(users)
                    userView?.changeProgressBarVisibility(View.GONE)
                }
            } else {
                fetchUsersData()
            }
        }
    }

    private fun fetchUsersData() {
        val apiService = ApiInterface.create()
        val usersCall = apiService.getUsers()
        usersCall.enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                userView?.showToast("Something went wrong", Toast.LENGTH_SHORT)
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    doAsync {
                        database?.userDao()?.insertAll(response.body()!!)
                    }
                    userView?.setUpdatedAt(Date().time)
                    userView?.addUsers(response.body())
                    userView?.changeProgressBarVisibility(View.GONE)
                } else {
                    userView?.showToast("Something went wrong", Toast.LENGTH_SHORT)
                }
            }

        })
    }

}