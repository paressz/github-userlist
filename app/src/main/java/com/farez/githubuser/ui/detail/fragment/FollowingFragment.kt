package com.farez.githubuser.ui.detail.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farez.githubuser.R
import com.farez.githubuser.databinding.FragmentFollowingBinding
import com.farez.githubuser.data.viewmodel.FollowingViewModel
import com.farez.githubuser.ui.detail.activity.DetailActivity
import com.farez.githubuser.ui.main.UserAdapter


class FollowingFragment : Fragment(R.layout.fragment_following) {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var userRVAdapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = arguments?.getString(DetailActivity.EXTRA).toString()
        _binding = FragmentFollowingBinding.bind(view)
        userRVAdapter = UserAdapter()
        userRVAdapter.notifyDataSetChanged()


        showLoading(true)
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java
        )
        followingViewModel.setListFollowing(username)
        followingViewModel.getListFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                showLoading(false)
                userRVAdapter.setList(it)
                if (userRVAdapter.itemCount == 0) binding.textView2.visibility = View.VISIBLE
            } else {
                followingViewModel.showErrorToast(requireContext(), "terjadi error")
            }

        }

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = userRVAdapter
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar2.visibility = if (state) View.VISIBLE else View.GONE
    }

}