package com.submission.dicoding.dgitapp.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.submission.dicoding.dgitapp.R
import com.submission.dicoding.dgitapp.data.Resource
import com.submission.dicoding.dgitapp.data.local.entity.FavoriteUserEntity
import com.submission.dicoding.dgitapp.data.remote.response.UserDetailResponse
import com.submission.dicoding.dgitapp.databinding.ActivityDetailUserBinding
import com.submission.dicoding.dgitapp.ui.detail.SectionPagerAdapter.Companion.TAB_TITLES
import com.submission.dicoding.dgitapp.utils.gone
import com.submission.dicoding.dgitapp.utils.setImageUrl
import com.submission.dicoding.dgitapp.utils.toShortNumberDisplay
import com.submission.dicoding.dgitapp.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel: DetailUserViewModel by viewModel()
    private var username: String? = null
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent?.getStringExtra(GITHUB_USER)

        if (savedInstanceState == null) {
            username?.let { detailViewModel.userDetail(it) }
        }

        detailViewModel.getUserdetail.observe(this) { user ->
            if (user != null) {
                when (user) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Error -> {
                        showLoading(false)
                        showError(user.message)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        user.data?.let { setUserDetail(it) }
                    }
                }
            }
        }

        initViewPager()

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = username
        }
    }

    private fun initViewPager() {
        val sectionPagerAdapter = SectionPagerAdapter(this, username)
        binding.vpFollowRepo.apply {
            adapter = sectionPagerAdapter
            offscreenPageLimit = 3
        }
        val mediator = TabLayoutMediator(binding.tabLayout, binding.vpFollowRepo) { tab, pos ->
            tab.text = when (pos) {
                0 -> getString(TAB_TITLES[0])
                1 -> getString(TAB_TITLES[1])
                else -> getString(TAB_TITLES[2])
            }
        }
        mediator.attach()
    }

    private fun setUserDetail(data: UserDetailResponse) {
        binding.ivUser.setImageUrl(this, data.avatarUrl, binding.pbImageUser)
        binding.apply {
            tvUsername.text = data.name
            tvCompany.text = data.company
            tvLocation.text = data.location
            tvFollowers.text = data.followers.toShortNumberDisplay()
            tvFollowings.text = data.following.toShortNumberDisplay()
            tvRepository.text = data.publicRepos.toShortNumberDisplay()
        }

        checkFavoriteUser(data.id.toString())
        val favoriteUser =
            FavoriteUserEntity(data.id.toString(), data.login, data.avatarUrl, data.html_url)
        binding.fabFavorite.setOnClickListener {
            if (isFavorite) {
                detailViewModel.deleteFavoriteUser(favoriteUser)
                Toast.makeText(
                    this,
                    getString(R.string.message_remove_favorite),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                detailViewModel.saveToFavorite(favoriteUser)
                Toast.makeText(this, getString(R.string.message_add_favorite), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun checkFavoriteUser(id: String) {
        detailViewModel.isFavoriteUser(id).observe(this) { user ->
            if (user) {
                isFavorite = true
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this, R.drawable.ic_favorited
                    )
                )
            } else {
                isFavorite = false
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        this, R.drawable.ic_favorite
                    )
                )
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbDetailUser.visible()
        } else {
            binding.pbDetailUser.gone()
        }
    }

    private fun showError(message: String?) {
        Toast.makeText(this@DetailUserActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val GITHUB_USER = "github_user"
        fun start(context: Context, user: String) {
            Intent(context, DetailUserActivity::class.java).apply {
                this.putExtra(GITHUB_USER, user)
                context.startActivity(this)
            }
        }
    }
}