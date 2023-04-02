package com.farez.githubuser.ui.detail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farez.githubuser.R
import com.farez.githubuser.databinding.FragmentFollowersBinding
import com.farez.githubuser.data.viewmodel.FollowersViewModel
import com.farez.githubuser.ui.detail.activity.DetailActivity
import com.farez.githubuser.ui.main.UserAdapter

class FollowersFragment : Fragment(R.layout.fragment_followers) {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var userRVAdapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = arguments?.getString(DetailActivity.EXTRA).toString()
        _binding = FragmentFollowersBinding.bind(view)
        userRVAdapter = UserAdapter()
        userRVAdapter.notifyDataSetChanged()


        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowersViewModel::class.java
        )
        viewModel.setListFollower(username)
        viewModel.getListFollower().observe(viewLifecycleOwner) {
            if (it != null) {
                showLoading(false)
                userRVAdapter.setList(it)
                if (userRVAdapter.itemCount == 0) {
                    binding.textView.visibility = View.VISIBLE
                    binding.textView3.visibility = View.VISIBLE
                }
            } else {
                viewModel.showErrorToast(requireContext(), "terjadi error")
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
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar2.visibility = if (state) View.VISIBLE else View.GONE
    }


}