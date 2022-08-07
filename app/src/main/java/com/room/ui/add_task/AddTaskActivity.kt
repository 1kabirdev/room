package com.room.ui.add_task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.room.App
import com.room.dao.DaoRoom
import com.room.databinding.ActivityAddTaskBinding
import com.room.model.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var tasksDao: DaoRoom

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tasksDao = (application as App).getDatabase().tasksDao()

        with(binding) {
            imageViewBack.setOnClickListener { finish() }
            saveRecord.setOnClickListener {
                if (name.text.toString().isNotEmpty() && description.text.toString()
                        .isNotEmpty()
                ) {
                    saveData(
                        name.text.toString(),
                        description.text.toString()
                    )
                } else {
                    Snackbar.make(
                        constraintLayout,
                        "Запись не должна быть пустой",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun saveData(name: String, description: String) =
        lifecycleScope.launch(Dispatchers.IO) {
            val tasks = Tasks(0, name, description)
            tasksDao.insertTasks(tasks)
            finish()
        }
}