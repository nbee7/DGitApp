package com.submission.dicoding.dgitapp.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.submission.dicoding.dgitapp.R
import com.submission.dicoding.dgitapp.data.local.entity.FavoriteUserEntity
import com.submission.dicoding.dgitapp.databinding.ActivityFavoriteUserBinding
import com.submission.dicoding.dgitapp.ui.detail.DetailUserActivity
import com.submission.dicoding.dgitapp.utils.FavoriteUserShareCallback
import com.submission.dicoding.dgitapp.utils.OnFavoriteUserItemClickCallback
import com.submission.dicoding.dgitapp.utils.gone
import com.submission.dicoding.dgitapp.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteUserActivity : AppCompatActivity(), OnFavoriteUserItemClickCallback,
    FavoriteUserShareCallback {
    private lateinit var binding: ActivityFavoriteUserBinding
    private val favoriteViewModel: FavoriteUserViewModel by viewModel()
    private lateinit var favoriteUserAdapter: FavoriteUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pageTittle = "Favorite User"

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = pageTittle
        }

        itemTouchHelper.attachToRecyclerView(binding.rvListUser)

        favoriteUserAdapter = FavoriteUserAdapter(this, this)
        binding.rvListUser.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = favoriteUserAdapter
        }

        favoriteViewModel.getAllFavoriteUser().observe(this) { user ->
            if (user.isNullOrEmpty()) {
                binding.rvListUser.gone()
                binding.lottieEmpty.visible()
            } else {
                binding.lottieEmpty.gone()
                binding.rvListUser.visible()
                favoriteUserAdapter.submitList(user)
            }
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int = makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val swipePosition = viewHolder.absoluteAdapterPosition
            val favoritUser = favoriteUserAdapter.getSwipedData(swipePosition)
            favoritUser?.let { favoriteViewModel.deleteFavoriteUser(it) }

            val snackbar = Snackbar.make(
                binding.root.rootView,
                R.string.message_undo_delete,
                Snackbar.LENGTH_SHORT
            )
            snackbar.setAction("YES") { _ ->
                favoritUser?.let { favoriteViewModel.saveToFavorite(it) }
            }
            snackbar.show()
        }
    })

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