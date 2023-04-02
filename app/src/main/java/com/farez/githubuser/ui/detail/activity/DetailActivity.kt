package com.farez.githubuser.ui.detail.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.farez.githubuser.databinding.ActivityDetailBinding
import com.farez.githubuser.data.viewmodel.DetailViewModel
import com.farez.githubuser.ui.detail.UserDetailAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA = "EXTRA"
        const val EXTRA_ID = "EXTRAID"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val bundle = Bundle()
        bundle.putString(EXTRA, username.toString())
        val userDetailAdapter = UserDetailAdapter(this, bundle)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        viewModel.setUSerDetail(username!!)
        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                var title = arrayOf("Following (${it.following})", "Followers (${it.followers})")
                binding.apply {
                    progressBar3.visibility = View.GONE
                    namaTextView.text = it.name
                    usernameDetailTextView.text = it.login
                    Glide.with(this@DetailActivity).load(it.avatarUrl)
                        .transition(DrawableTransitionOptions.withCrossFade()).into(detailImageVIew)
                    viewPager.adapter = userDetailAdapter
                    TabLayoutMediator(tabs, viewPager) { tab, position ->
                        tab.text = title[position]
                    }.attach()
                }
            }
        }
        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toggleButton.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toggleButton.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleButton.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                viewModel.addToFav(username, id)
                Toast.makeText(this,"DITAMBAHKAN KE FAVORIT",Toast.LENGTH_SHORT).show()
            } else {
                viewModel.deleteFromFav(id)
                Toast.makeText(this,"DIHAPUS DARI FAVORIT",Toast.LENGTH_SHORT).show()

            }
            binding.toggleButton.isChecked = _isChecked

        }

        supportActionBar?.hide()

    }
}