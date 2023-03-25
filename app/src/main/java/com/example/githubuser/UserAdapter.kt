package com.example.githubuser

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.MainThread
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.databinding.ItemRowBinding

class UserAdapter(private val listUser : List<ItemsItem>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        var image : ImageView = binding.imgItemPhoto
        var tvItemName: TextView = binding.tvItemName

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : UserAdapter.ViewHolder{
        val binding  =ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }



    override fun getItemCount()= listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)   {
        val pos = listUser[position]
        holder.tvItemName.text= pos.login
        Glide.with(holder.itemView).load(pos.avatarUrl).into(holder.image)
    }
}