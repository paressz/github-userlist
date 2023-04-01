package com.farez.githubuser.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.farez.githubuser.ui.detail.fragment.FollowersFragment
import com.farez.githubuser.ui.detail.fragment.FollowingFragment

class UserDetailAdapter( activity: AppCompatActivity, bundle: Bundle) : FragmentStateAdapter(activity) {
    private var fbundle : Bundle
    init {
        fbundle = bundle
    }
    override fun getItemCount(): Int {
        return 2

    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingFragment()
            1 -> fragment = FollowersFragment()
        }
        fragment?.arguments = this.fbundle
        return fragment as Fragment
    }

}