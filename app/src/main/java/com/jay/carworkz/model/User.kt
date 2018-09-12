package com.jay.carworkz.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(
        @SerializedName("login") val login: String,
        @PrimaryKey @SerializedName("id") val userId: Int,
        @SerializedName("avatar_url") val avatarUrl: String,
        @SerializedName("url") val url: String
)