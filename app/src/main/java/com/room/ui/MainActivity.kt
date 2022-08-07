package com.room.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.room.App
import com.room.dao.DaoRoom
import com.room.databinding.ActivityMainBinding
import com.room.model.Tasks
import com.room.ui.add_task.AddTaskActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), AdapterTasks.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tasksDao: DaoRoom
    private lateinit var adapterUsers: AdapterTasks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tasksDao = (application as App).getDatabase().tasksDao()

        onClickListener()
    }

    override fun onStart() {
        loadTasks()
        super.onStart()
    }

    private fun loadTasks() {
        lifecycleScope.launch(Dispatchers.Main) {
            val list = tasksDao.getAllTasks() as ArrayList
            withContext(Dispatchers.Main) {
                if (list.size == 0) {
                    binding.linearNotRecord.visibility = View.VISIBLE
                } else {
                    binding.linearNotRecord.visibility = View.GONE
                    adapterUsers = AdapterTasks(list, this@MainActivity)
                    binding.recyclerView.adapter = adapterUsers
                }
            }
        }
    }

    private fun onClickListener() {
        binding.addRecord.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }
    }

    override fun delete(tasks: Tasks) {
        lifecycleScope.launch(Dispatchers.IO) {
            tasksDao.deleteTasks(tasks)
        }
    }

    override fun notTasks() {
        binding.linearNotRecord.visibility = View.VISIBLE
    }

    override fun onResume() {
        loadTasks()
        super.onResume()
    }
}