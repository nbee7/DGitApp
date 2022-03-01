package com.submission.dicoding.dgitapp.ui.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.dicoding.dgitapp.data.UserEntity
import com.submission.dicoding.dgitapp.databinding.ActivityMainBinding
import com.submission.dicoding.dgitapp.ui.detail.DetailUserActivity
import com.submission.dicoding.dgitapp.utils.OnUserItemClickCallback
import com.submission.dicoding.dgitapp.utils.ShareCallback
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.ArrayList

class MainActivity : AppCompatActivity(), OnUserItemClickCallback, ShareCallback {
    private lateinit var binding: ActivityMainBinding
    private val listUsers = ArrayList<UserEntity>()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listUserFromAsset = loadUserGithub()
        listUsers.addAll(listUserFromAsset)
        showRecycleView()
    }

    private fun showRecycleView(){
        mainAdapter = MainAdapter(listUsers, this, this)
        binding.rvListUser.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = mainAdapter
        }
    }

    private fun parsingFileToString(): String? {
        return try {
            val `is` = assets.open("githubuser.json")
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)

        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    private fun loadUserGithub(): List<UserEntity> {
        val list = ArrayList<UserEntity>()
        try {
            val responseObject = JSONObject(parsingFileToString().toString())
            val listArray = responseObject.getJSONArray("users")
            for (i in 0 until listArray.length()) {
                val course = listArray.getJSONObject(i)

                val username = course.getString("username")
                val name = course.getString("name")
                val avatar = course.getString("avatar")
                val company = course.getString("company")
                val location = course.getString("location")
                val repos = course.getInt("repository")
                val followers = course.getInt("follower")
                val followings = course.getInt("following")

                val usersItemResponse = UserEntity(
                    followers,
                    followings,
                    name,
                    company,
                    location,
                    avatar,
                    repos,
                    username
                )
                list.add(usersItemResponse)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }

    override fun onUserItemClicked(data: UserEntity) {
        DetailUserActivity.start(this, data)
    }

    override fun onShareClick(data: UserEntity) {
        val dataShare = """
                        User Github
                        Username    : ${data.username}
                        Followers   : ${data.follower}
                        Followings  : ${data.following}
                        Repository  : ${data.repository}
                        Link Github : https://github.com/${data.username}
                    """.trimIndent()
        val intent = Intent(Intent.ACTION_SEND)
        intent.apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT,dataShare)
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