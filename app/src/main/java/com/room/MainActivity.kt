package com.room

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.room.dao.DaoRoom
import com.room.databinding.ActivityMainBinding
import com.room.model.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), AdapterUsers.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var usersDao: DaoRoom
    private lateinit var adapterUsers: AdapterUsers
    private lateinit var userList: ArrayList<Users>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        usersDao = (application as App).getDatabase().usersDao()
        userList = ArrayList()

        binding.save.setOnClickListener {
            saveData(
                binding.name.text.toString(),
                binding.email.text.toString()
            )
        }
        binding.clear.setOnClickListener {
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
        binding.recyclerView.adapter = adapterUsers
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun deleteData() {
        lifecycleScope.launch(Dispatchers.IO) {
            usersDao.deleteAll()
        }
        adapterUsers.notifyDataSetChanged()
        binding.recyclerView.adapter = null
        userList.clear()
    }

    private fun loadData() {
        lifecycleScope.launch(Dispatchers.IO) {
            userList.addAll(usersDao.getAll())
            binding.recyclerView.adapter = adapterUsers
        }
    }

    private fun clearEditText() {
        binding.name.text = null
        binding.email.text = null
    }

    override fun delete(users: Users) {
        lifecycleScope.launch(Dispatchers.IO) {
            usersDao.delete(users)
        }
    }
}