package com.jay.carworkz.user

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jay.carworkz.R
import com.jay.carworkz.model.User


class UserAdapter(private val context: Context, private var userList: ArrayList<User>, private var userListFiltered: ArrayList<User>, private val listener: UserAdapterListener) : RecyclerView.Adapter<UserAdapter.MyViewHolder>(), Filterable {

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                userListFiltered = if (charString.isEmpty()) {
                    userList
                } else {
                    val filteredList = mutableListOf<User>()
                    for (row in userList) {
                        if (row.login.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    filteredList as ArrayList<User>
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = userListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                userListFiltered = filterResults.values as ArrayList<User>
                notifyDataSetChanged()
            }
        }
    }

    fun addUsers(users: ArrayList<User>) {
        userList = users
        userListFiltered = users
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.name)
        var phone: TextView = view.findViewById(R.id.url)
        var thumbnail: ImageView = view.findViewById(R.id.thumbnail)

        init {
            view.setOnClickListener {
                listener.onUserSelected(userListFiltered[adapterPosition])
            }
        }
    }


    init {
        this.userListFiltered = userList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_row_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = userListFiltered[position]
        holder.name.text = user.login
        holder.phone.text = user.url
        Glide.with(context)
                .load(user.avatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail)
    }

    override fun getItemCount(): Int {
        return userListFiltered.size
    }

    interface UserAdapterListener {
        fun onUserSelected(user: User)
    }
}