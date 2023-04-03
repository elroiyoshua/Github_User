package com.example.githubuser.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        showLoading(true)
        val username = intent.getStringExtra(TAG_USERNAME)
        val id = intent.getIntExtra(TAG_ID,0)
        val avatarUrl = intent.getStringExtra(TAG_URL)
        val bundle = Bundle()
        bundle.putString(TAG_USERNAME,username)

        viewModel = ViewModelProvider(this).get(
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
                    showLoading(false)
                }
            }
        })

        var _isChecked= false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if(count>0){
                        binding.toggleFavorite.isChecked = true
                        _isChecked = true
                    }else{
                        binding.toggleFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener{
            _isChecked = !_isChecked

            if (_isChecked){
                if (username != null) {
                    if (avatarUrl != null) {
                        viewModel.addToFavorite(username,id,avatarUrl)
                        //it.setBackgroundResource(R.drawable.baseline_favorite_24)
                        Toast.makeText(this,"$username telah ditambahkan sebagai akun Favorite",Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                viewModel.removeFromFavorite(id)
                //it.setBackgroundResource(R.drawable.baseline_favorite2_24)
                Toast.makeText(this,"$username telah dihapus dari akun Favorite",Toast.LENGTH_SHORT).show()
            }
            binding.toggleFavorite.isChecked = _isChecked
        }


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
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarDet.visibility = View.VISIBLE

        } else {
            binding.progressBarDet.visibility = View.INVISIBLE

        }

    }
    companion object{
        const val TAG_USERNAME = "username"
        const val TAG_ID = "id"
        const val TAG_URL ="url"
    }

}