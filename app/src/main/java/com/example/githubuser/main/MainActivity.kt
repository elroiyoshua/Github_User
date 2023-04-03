package com.example.githubuser.main

import com.example.githubuser.detail.DetailUserActivity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.Favorite.FavoriteActivity
import com.example.githubuser.model.ItemsItem
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.setting.SettingActivity
import com.example.githubuser.setting.SettingPreferences
import com.example.githubuser.setting.SettingViewModel
import com.example.githubuser.setting.ViewModelFactory

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref =  SettingPreferences.getInstance(datastore)
        val mainViewModel = ViewModelProvider(this,ViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )
        mainViewModel.getThemeSettings().observe(this){
            if (it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


        adapter = UserAdapter()
        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.hasFixedSize()
            rvUser.adapter = adapter
        }
        showLoading(true)
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.TAG_USERNAME,data.login)
                    it.putExtra(DetailUserActivity.TAG_ID,data.id)
                    it.putExtra(DetailUserActivity.TAG_URL,data.avatarUrl)

                    startActivity(it)
                }
            }

        })
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)
        binding.apply {
            if (adapter.itemCount == 0){
                viewModel.setSearch("elroi")
            }else{
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.getSearchUssers().observe(this,{
            if(it!= null){
                showLoading(false)
                adapter.setList(it)
            }
        })


    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)


        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                if (query == null){
                    Toast.makeText(this@MainActivity, "Masukan Username", Toast.LENGTH_SHORT).show()
                    showLoading(false)
                    searchView.clearFocus()
                }else{
                    showLoading(true)
                    viewModel.setSearch(query)
                    searchView.clearFocus()
                }
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite_menu ->{
                Intent(this,FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.setting_activity->{
                Intent(this,SettingActivity::class.java).also{
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvUser.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.rvUser.visibility = View.VISIBLE
        }
    }
}