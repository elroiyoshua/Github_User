package com.example.githubuser.Main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.Model.ItemsItem
import com.example.githubuser.databinding.ItemRowBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private val listUser =  ArrayList<ItemsItem>()
    private var onItemClickCallback: OnItemClickCallback?= null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
    fun setList (user: ArrayList<ItemsItem>){
        listUser.clear()
        listUser.addAll(user)
        notifyDataSetChanged()
    }
    fun setList2(user: ArrayList<ItemsItem>){
        listUser.addAll(user)
    }
    inner class ViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user : ItemsItem){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
            binding.apply {
                Glide.with(itemView).load(user.avatarUrl).into(imgItemPhoto)
                tvItemName.text = user.login
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val binding  =ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount()= listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)   {
        holder.bind(listUser[position])
    }

    interface OnItemClickCallback{
        fun onItemClicked(data : ItemsItem)
    }
}