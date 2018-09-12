package com.jay.carworkz.user

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.jay.carworkz.R
import com.jay.carworkz.model.User
import com.jay.carworkz.utility.Constants
import com.jay.carworkz.utility.MyDividerItemDecoration
import kotlinx.android.synthetic.main.activity_user.*
import java.util.*


class UserActivity : AppCompatActivity(), UserView, UserAdapter.UserAdapterListener {

    private lateinit var userPresenter: UserPresenter
    private var mAdapter: UserAdapter? = null
    private var userList: List<User>? = null
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        title = "Users"
        userPresenter = UserPresenter()
        onAttach()

        userList = mutableListOf()
        mAdapter = UserAdapter(this, userList as ArrayList<User>, userList as ArrayList<User>, this)

        recycler_view.layoutManager = LinearLayoutManager(this@UserActivity)
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.addItemDecoration(MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36))
        recycler_view.adapter = mAdapter

        userPresenter.getUsers(this@UserActivity)
    }

    override fun onDestroy() {
        super.onDestroy()
        onDetach()
    }

    override fun showToast(message: String?, length: Int) {
        Toast.makeText(this@UserActivity, message, length).show()
    }

    override fun onAttach() {
        userPresenter.onAttach(this)
    }

    override fun onDetach() {
        userPresenter.onDetach()
    }

    override fun onUserSelected(user: User) {
        showToast(user.login, Toast.LENGTH_SHORT)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_user, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView?.maxWidth = Integer.MAX_VALUE

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mAdapter?.filter?.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                mAdapter?.filter?.filter(query)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (!searchView?.isIconified!!) {
            searchView?.isIconified = true
            return
        }
        super.onBackPressed()
    }

    override fun addUsers(users: List<User>?) {
        mAdapter?.addUsers(users as ArrayList<User>)
        mAdapter?.notifyDataSetChanged()
    }

    override fun changeProgressBarVisibility(visibility: Int) {
        progress_bar.visibility = visibility
    }

    override fun setUpdatedAt(updatedAt: Long) {
        val prefs = this.getSharedPreferences(Constants.UPDATED_AT_PREFS_FILENAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putLong(Constants.UPDATED_AT, updatedAt)
        editor.apply()
    }

    override fun getUpdatedAt(): Long {
        val prefs = this.getSharedPreferences(Constants.UPDATED_AT_PREFS_FILENAME, Context.MODE_PRIVATE)
        return prefs?.getLong(Constants.UPDATED_AT, Date().time - 700000)!!
    }
}
