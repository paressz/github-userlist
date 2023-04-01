package com.farez.githubuser.ui.detail.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.farez.githubuser.databinding.ActivityDetailBinding
import com.farez.githubuser.data.viewmodel.DetailViewModel
import com.farez.githubuser.ui.detail.UserDetailAdapter
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA = "EXTRA"
    }
    private lateinit var binding : ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA)
        val bundle = Bundle()
        bundle.putString(EXTRA, username.toString())
        val userDetailAdapter = UserDetailAdapter(this, bundle)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory())
            .get(DetailViewModel::class.java)

        viewModel.setUSerDetail(username!!)
        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                var title = arrayOf("Following (${it.following})","Followers (${it.followers})")
                binding.apply {
                    progressBar3.visibility = View.GONE
                    namaTextView.text = it.name
                    usernameDetailTextView.text = it.login
                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(detailImageVIew)
                    binding.viewPager.adapter = userDetailAdapter
                    TabLayoutMediator(binding.tabs, binding.viewPager) {
                            tab, position ->
                        tab.text = title[position]
                    }.attach()
                }
            }
        }
        supportActionBar?.hide()
    }
}