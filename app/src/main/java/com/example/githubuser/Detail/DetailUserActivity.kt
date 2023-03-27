package com.example.githubuser.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuser.Detail.SectionPagerAdapter
import com.example.githubuser.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {
    companion object{
        const val TAG_USERNAME = "username"
    }
    private lateinit var binding : ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(TAG_USERNAME)
        val bundle = Bundle()
        bundle.putString(TAG_USERNAME,username)

        viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(
            DetailUserViewModel::class.java)

        if (username != null) {
            viewModel.setUserDetail(username)
        }
        viewModel.getUserDetail().observe(this,{
            if (it != null){
                binding.apply {
                    tvLogin.text = it.login
                    tvUsername.text = it.name
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailUserActivity).load(it.avatarUrl).into(imgUserDetail)
                }
            }
        })
        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager,bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tablayouts.setupWithViewPager(viewPager)

        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}