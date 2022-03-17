package com.submission.dicoding.dgitapp.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.dicoding.dgitapp.data.local.entity.FavoriteUserEntity
import com.submission.dicoding.dgitapp.databinding.ActivityMainBinding
import com.submission.dicoding.dgitapp.ui.detail.DetailUserActivity
import com.submission.dicoding.dgitapp.utils.FavoriteUserShareCallback
import com.submission.dicoding.dgitapp.utils.OnFavoriteUserItemClickCallback
import com.submission.dicoding.dgitapp.utils.gone
import com.submission.dicoding.dgitapp.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteUserActivity : AppCompatActivity(), OnFavoriteUserItemClickCallback,
    FavoriteUserShareCallback {
    private lateinit var binding: ActivityMainBinding
    private val favoriteViewModel: FavoriteUserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pageTittle = "Favorite User"

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = pageTittle
        }

        favoriteViewModel.getAllFavoriteUser().observe(this) { user ->
            if (user.isNullOrEmpty()) {
                binding.rvListUser.gone()
                binding.txtEmpty.visible()
            } else {
                binding.txtEmpty.gone()
                binding.rvListUser.visible()
                val favoriteUserAdapter = FavoriteUserAdapter(user, this, this)
                binding.rvListUser.apply {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = favoriteUserAdapter
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFavoriteUserItemClicked(data: FavoriteUserEntity) {
        data.username?.let { DetailUserActivity.start(this, it) }
    }

    override fun onFavoriteUserShareClick(data: FavoriteUserEntity) {
        val dataShare = """
                        User Github
                        Username    : ${data.username}
                        Link Github : ${data.url}
                    """.trimIndent()
        val intent = Intent(Intent.ACTION_SEND)
        intent.apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, dataShare)
        }
        startActivity(Intent.createChooser(intent, "Share to Your Friends"))
    }
}