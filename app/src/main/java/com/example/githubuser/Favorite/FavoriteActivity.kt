package com.example.githubuser.Favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.datalocal.FavoriteUser
import com.example.githubuser.detail.DetailUserActivity
import com.example.githubuser.main.UserAdapter
import com.example.githubuser.model.ItemsItem

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)


        showLoading(true)
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.TAG_USERNAME,data.login)
                    it.putExtra(DetailUserActivity.TAG_ID,data.id)
                    it.putExtra(DetailUserActivity.TAG_URL,data.avatarUrl)

                    startActivity(it)
                }
            }

        })
        binding.apply {
            rvFav.setHasFixedSize(true)
            rvFav.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFav.adapter = adapter
        }
        viewModel.getFavoriteUser()?.observe(this,{
            if(it != null){
                val list = mapList(it)
                adapter.setList(list)
            }
        })

    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<ItemsItem> {
        val listUser = ArrayList<ItemsItem>()

        for(user in users ){
            val userMapped = ItemsItem("","","","",user.login,"","","",""
            ,0,"",user.avatarUrl,"","",false,user.id,"","","")
            listUser.add(userMapped)
        }
        return listUser
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarFav.visibility = View.VISIBLE
            binding.rvFav.visibility = View.INVISIBLE
        } else {
            binding.progressBarFav.visibility = View.INVISIBLE
            binding.rvFav.visibility = View.VISIBLE
        }

    }
}