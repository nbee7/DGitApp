package com.submission.dicoding.dgitapp.ui.detail.userrepo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.dicoding.dgitapp.data.Resource
import com.submission.dicoding.dgitapp.data.remote.response.UserRepositoryResponse
import com.submission.dicoding.dgitapp.databinding.FragmentUserRepositoryBinding
import com.submission.dicoding.dgitapp.utils.OnRepositoryitemClickcallback
import com.submission.dicoding.dgitapp.utils.gone
import com.submission.dicoding.dgitapp.utils.visible

class UserRepositoryFragment : Fragment(), OnRepositoryitemClickcallback {
    private var _binding: FragmentUserRepositoryBinding? = null
    private val binding get() = _binding
    private var username: String? = null
    private val repoViewModel: UserRepositoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserRepositoryBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            repoViewModel.getUserRepository.observe(viewLifecycleOwner) { user ->
                if (user == null) {
                    username?.let { repoViewModel.userRepository(it) }
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

    private fun setRecycleview(listRepos: List<UserRepositoryResponse>) {
        if (listRepos.isNullOrEmpty()) {
            binding?.txtEmpty?.visible()
        } else {
            val mainAdapter = UserRepositoryAdapter(listRepos, this)
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

    override fun onItemRepositoryClicked(data: UserRepositoryResponse) {
        val url: Uri? = data.reposUrl?.toUri()
        val intent = Intent(Intent.ACTION_VIEW, url)
        startActivity(intent)
    }

    companion object {
        const val ARG_USERNAME = "arg_username"

        @JvmStatic
        fun newInstance(username: String?) =
            UserRepositoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, username)
                }
            }
    }
}