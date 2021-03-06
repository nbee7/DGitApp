package com.submission.dicoding.dgitapp.ui.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.dicoding.dgitapp.R
import com.submission.dicoding.dgitapp.data.Resource
import com.submission.dicoding.dgitapp.data.remote.response.UserItems
import com.submission.dicoding.dgitapp.databinding.ActivityMainBinding
import com.submission.dicoding.dgitapp.ui.detail.DetailUserActivity
import com.submission.dicoding.dgitapp.ui.favorite.FavoriteUserActivity
import com.submission.dicoding.dgitapp.ui.setting.SettingActivity
import com.submission.dicoding.dgitapp.utils.OnUserItemClickCallback
import com.submission.dicoding.dgitapp.utils.ShareCallback
import com.submission.dicoding.dgitapp.utils.gone
import com.submission.dicoding.dgitapp.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), OnUserItemClickCallback, ShareCallback {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = (menu.findItem(R.id.search)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = resources.getString(R.string.search_hint)
        }

        val searchEditText =
            searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
        TextViewCompat.setTextAppearance(searchEditText, R.style.searchText)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { mainViewModel.searchUser(it) }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    private fun setObserver() {
        mainViewModel.getUser.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Error -> {
                        showLoading(false)
                        showError(result.message)
                    }
                    is Resource.Success -> {
                        result.data?.let { setRecycleview(it) }
                    }
                }
            }
        }
    }

    private fun setRecycleview(listUser: List<UserItems>) {
        if (listUser.isNullOrEmpty()) {
            showLoading(false)
            binding.rvListUser.gone()
            binding.lottieEmpty.visible()
        } else {
            showLoading(false)
            binding.lottieEmpty.gone()
            binding.rvListUser.visible()
            val mainAdapter = MainAdapter(listUser, this, this)
            binding.rvListUser.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = mainAdapter
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.rvListUser.gone()
            binding.pbUser.visible()
            binding.lottieEmpty.gone()
        } else {
            binding.pbUser.gone()
        }
    }

    private fun showError(message: String?) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val mIntent = Intent(this@MainActivity, FavoriteUserActivity::class.java)
                startActivity(mIntent)
            }
            R.id.setting -> {
                val mIntent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onUserItemClicked(data: UserItems) {
        DetailUserActivity.start(this, data.login)
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
            putExtra(Intent.EXTRA_TEXT, dataShare)
        }
        startActivity(Intent.createChooser(intent, "Share to Your Friends"))
    }

    companion object {
        fun start(context: Context) {
            Intent(context, MainActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }
}