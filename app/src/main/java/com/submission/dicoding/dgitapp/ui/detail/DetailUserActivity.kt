package com.submission.dicoding.dgitapp.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.submission.dicoding.dgitapp.data.UserEntity
import com.submission.dicoding.dgitapp.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<UserEntity>(GITHUB_USER)
        val avatarSource: Int = resources.getIdentifier(user?.avatar, null, packageName)

        Glide.with(this)
            .load(avatarSource)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.ivUser)

        binding.apply {
            tvUsername.text = user?.username
            tvCompany.text = user?.company
            tvLocation.text = user?.location
            tvRepository.text = user?.repository.toString()
            tvFollowings.text = user?.following.toString()
            tvFollowers.text = user?.follower.toString()
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = user?.username
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
        fun start(context: Context, user: UserEntity ) {
            Intent(context, DetailUserActivity::class.java).apply {
                this.putExtra(GITHUB_USER, user)
                context.startActivity(this)
            }
        }
    }
}