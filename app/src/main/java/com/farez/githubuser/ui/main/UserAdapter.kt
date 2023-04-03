package com.farez.githubuser.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.farez.githubuser.data.model.User
import com.farez.githubuser.databinding.UserlistBinding

class UserAdapter() : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val listUser = ArrayList<User>()
    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun setList(users: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = UserlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    inner class UserViewHolder(val binding: UserlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.root.setOnClickListener {
                onItemClickListener?.onItemClick(user)
            }
            binding.apply {
                Glide.with(itemView).load(user.avatarUrl)
                    .transition(DrawableTransitionOptions.withCrossFade()).into(userImageView)

                usernameTextView.text = user.login

            }

        }
    }

    interface OnItemClickListener {
        fun onItemClick(data: User)
    }
}