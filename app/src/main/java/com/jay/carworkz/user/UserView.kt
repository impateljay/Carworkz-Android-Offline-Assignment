package com.jay.carworkz.user

import com.jay.carworkz.base.BaseView
import com.jay.carworkz.model.User

interface UserView : BaseView {
    fun showToast(message: String?, length: Int)
    fun addUsers(users: List<User>?)
    fun changeProgressBarVisibility(visibility: Int)
    fun setUpdatedAt(updatedAt: Long)
    fun getUpdatedAt(): Long
}