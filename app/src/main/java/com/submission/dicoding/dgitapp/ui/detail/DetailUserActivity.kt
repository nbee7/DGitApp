package com.submission.dicoding.dgitapp.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.submission.dicoding.dgitapp.data.remote.response.UserDetailResponse
import com.submission.dicoding.dgitapp.databinding.ActivityDetailUserBinding
import com.submission.dicoding.dgitapp.ui.detail.SectionPagerAdapter.Companion.TAB_TITLES
import com.submission.dicoding.dgitapp.utils.gone
import com.submission.dicoding.dgitapp.utils.setImageUrl
import com.submission.dicoding.dgitapp.utils.toShortNumberDisplay
import com.submission.dicoding.dgitapp.utils.visible

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel: DetailUserViewModel by viewModels()
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent?.getStringExtra(GITHUB_USER)

        initViewPager()

        detailViewModel.detailUser.observe(this) { data ->
            if (data == null) {
                username?.let { detailViewModel.getDetailUser(it) }
            } else {
                setUserDetail(data)
            }
        }

        detailViewModel.loading.observe(this) {
            showLoading(it)
        }

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
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbDetailUser.visible()
        } else {
            binding.pbDetailUser.gone()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val GITHUB_USER = "github_user"
        fun start(context: Context, user: String ) {
            Intent(context, DetailUserActivity::class.java).apply {
                this.putExtra(GITHUB_USER, user)
                context.startActivity(this)
            }
        }
    }
}