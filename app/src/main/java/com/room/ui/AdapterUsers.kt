package com.room.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.room.R
import com.room.model.Users

class AdapterUsers(
    private var users: ArrayList<Users>,
    private var listener: OnClickListener
) :
    RecyclerView.Adapter<AdapterUsers.ViewHolder>() {

    private lateinit var viewHolder: ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_users, parent, false)
        viewHolder = ViewHolder(holder)
        return viewHolder
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(user: Users) {
        users.add(user)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        viewHolder.bindView(holder, user)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var name: TextView = view.findViewById(R.id.nameView)
        private var email: TextView = view.findViewById(R.id.emailView)
        private var delete: TextView = view.findViewById(R.id.delete)

        @SuppressLint("NotifyDataSetChanged")
        fun bindView(holder: ViewHolder, user: Users) {
            holder.email.text = user.email
            holder.name.text = user.name
            holder.delete.setOnClickListener {
                listener.delete(user)
                users.remove(user)
                notifyDataSetChanged()
            }
        }
    }

    interface OnClickListener {
        fun delete(users: Users)
    }
}