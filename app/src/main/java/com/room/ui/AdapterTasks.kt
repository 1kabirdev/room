package com.room.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.room.databinding.ItemTasksBinding
import com.room.model.Tasks

class AdapterTasks(
    private var tasksList: ArrayList<Tasks>,
    private var listener: OnClickListener
) :
    RecyclerView.Adapter<AdapterTasks.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemTasksBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindView(tasksList[position])

    override fun getItemCount(): Int = tasksList.size

    inner class ViewHolder(private val binding: ItemTasksBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bindView(tasks: Tasks) {
            with(binding) {
                nameTextViewView.text = tasks.title
                descriptionTextView.text = tasks.description
                delete.setOnClickListener {
                    listener.delete(tasks)
                    notRecords(tasks)
                    notifyDataSetChanged()
                }
            }
        }

        private fun notRecords(tasks: Tasks) {
            tasksList.remove(tasks)
            if (tasksList.size == 0) {
                listener.notTasks()
            }
        }
    }


    interface OnClickListener {
        fun delete(tasks: Tasks)
        fun notTasks()
    }
}