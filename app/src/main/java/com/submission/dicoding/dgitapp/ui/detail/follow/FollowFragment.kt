package com.submission.dicoding.dgitapp.ui.detail.follow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.dicoding.dgitapp.data.Resource
import com.submission.dicoding.dgitapp.data.remote.response.UserItems
import com.submission.dicoding.dgitapp.databinding.FragmentFollowBinding
import com.submission.dicoding.dgitapp.ui.detail.DetailUserActivity
import com.submission.dicoding.dgitapp.ui.home.MainAdapter
import com.submission.dicoding.dgitapp.utils.OnUserItemClickCallback
import com.submission.dicoding.dgitapp.utils.ShareCallback
import com.submission.dicoding.dgitapp.utils.gone
import com.submission.dicoding.dgitapp.utils.visible

class FollowFragment : Fragment(), OnUserItemClickCallback, ShareCallback {
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding
    private val followViewModel: FollowViewModel by viewModels()
    private var sectionIndex: Int? = 0
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sectionIndex = it.getInt(ARG_SECTION_INDEX)
            username = it.getString(ARG_USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            setUpViewPager()
        }
    }

    private fun setUpViewPager() {
        when (sectionIndex) {
            1 -> followViewModel.getUserFollowers.observe(viewLifecycleOwner) { user ->
                if (user == null) {
                    username?.let { followViewModel.userFollowers(it) }
                } else {
                    when (user) {
                        is Resource.Loading -> showLoading(true)
                        is Resource.Error -> {
                            showLoading(false)
                            showError(user.message)
                        }
                        is Resource.Success -> {
                            showLoading(false)
                            user.data?.let { setRecycleview(it) }
                        }
                    }
                }
            }
            2 -> followViewModel.getUserFollowings.observe(viewLifecycleOwner) { user ->
                if (user == null) {
                    username?.let { followViewModel.userFollowings(it) }
                } else {
                    when (user) {
                        is Resource.Loading -> showLoading(true)
                        is Resource.Error -> {
                            showLoading(false)
                            showError(user.message)
                        }
                        is Resource.Success -> {
                            showLoading(false)
                            user.data?.let { setRecycleview(it) }
                        }
                    }
                }
            }
        }
    }

    private fun setRecycleview(listUser: List<UserItems>) {
        if (listUser.isNullOrEmpty()) {
            binding?.txtEmpty?.visible()
        } else {
            val mainAdapter = MainAdapter(listUser, this, this)
            binding?.rvListUser?.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = mainAdapter
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding?.rvListUser?.gone()
            binding?.pbUser?.visible()
        } else {
            binding?.rvListUser?.visible()
            binding?.pbUser?.gone()
        }
    }

    private fun showError(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onUserItemClicked(data: UserItems) {
        DetailUserActivity.start(requireContext(), data.login)
    }

    override fun onShareClick(data: UserItems) {
        val dataShare = """
                        User Github
                        Username    : ${data.login}
                        Link Github : ${data.htmlUrl}
                    """.trimIndent()
        val intent = Intent(Intent.ACTION_SEND)
        intent.apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT,dataShare)
        }
        startActivity(Intent.createChooser(intent, "Share to Your Friends"))
    }

    companion object {
        const val ARG_SECTION_INDEX = "arg_section_index"
        const val ARG_USERNAME = "arg_username"

        @JvmStatic
        fun newInstance(index: Int, username: String?) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_INDEX, index)
                    putString(ARG_USERNAME, username)
                }
            }
    }
}