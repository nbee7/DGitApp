package com.submission.dicoding.dgitapp.ui.detail

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.submission.dicoding.dgitapp.R
import com.submission.dicoding.dgitapp.ui.detail.follow.FollowFragment
import com.submission.dicoding.dgitapp.ui.detail.userrepo.UserRepositoryFragment

class SectionPagerAdapter(activity: AppCompatActivity, private val username: String?): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserRepositoryFragment.newInstance(username)
            else -> FollowFragment.newInstance(position, username)
        }
    }

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(R.string.repository, R.string.followers, R.string.followings)
    }
}