package com.room

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.room.dao.UsersDao
import com.room.model.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), AdapterUsers.OnClickListener {
    private lateinit var usersDao: UsersDao

    private lateinit var name: AppCompatEditText
    private lateinit var email: AppCompatEditText
    private lateinit var save: AppCompatButton
    private lateinit var clear: AppCompatButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterUsers: AdapterUsers
    private lateinit var userList: ArrayList<Users>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usersDao = (application as App).getDatabase().usersDao()

        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        save = findViewById(R.id.save)
        clear = findViewById(R.id.clear)
        recyclerView = findViewById(R.id.recyclerView)
        userList = ArrayList()

        save.setOnClickListener {
            saveData(name.text.toString(), email.text.toString())
        }
        clear.setOnClickListener {
            deleteData()
        }

        loadData()
        adapterUsers = AdapterUsers(userList, this@MainActivity)

    }


    @SuppressLint("NotifyDataSetChanged")
    private fun saveData(name: String, email: String) {

        lifecycleScope.launch(Dispatchers.IO) {
            //Insert
            val user = Users(0, name, email)
            usersDao.insert(user)
            adapterUsers.addItem(user)
            clearEditText()
            Log.i("MyTAG", "inserted")
        }

        adapterUsers.notifyDataSetChanged()
        recyclerView.adapter = adapterUsers
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun deleteData() {
        lifecycleScope.launch(Dispatchers.IO) {
            usersDao.deleteAll()
        }
        adapterUsers.notifyDataSetChanged()
        recyclerView.adapter = null
        userList.clear()
    }

    private fun loadData() {
        lifecycleScope.launch(Dispatchers.IO) {
            userList.addAll(usersDao.getAll())
            recyclerView.adapter = adapterUsers
        }
    }

    private fun clearEditText() {
        name.text = null
        email.text = null
    }

    override fun delete(users: Users) {
        lifecycleScope.launch(Dispatchers.IO) {
            usersDao.delete(users)
        }
    }
}