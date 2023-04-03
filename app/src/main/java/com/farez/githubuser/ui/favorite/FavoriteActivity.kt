package com.farez.githubuser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farez.githubuser.data.local.FavoriteUser
import com.farez.githubuser.data.model.User
import com.farez.githubuser.data.viewmodel.FavoriteViewModel
import com.farez.githubuser.databinding.ActivityFavoriteBinding
import com.farez.githubuser.ui.detail.activity.DetailActivity
import com.farez.githubuser.ui.main.UserAdapter

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickListener(object : UserAdapter.OnItemClickListener {
            override fun onItemClick(data: User) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatarUrl)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            recyclerView.adapter = adapter
        }

        viewModel.getFav()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        }
        supportActionBar?.hide()
    }

    private fun mapList(list: List<FavoriteUser>): ArrayList<User> {
        val listUser = ArrayList<User>()
        for (user in list) {
            val userMapped = User(user.avatar_url, user.id, user.login)
            listUser.add(userMapped)
        }
        return listUser
    }
}