package com.submission.dicoding.dgitapp.ui.detail.userrepo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.submission.dicoding.dgitapp.data.remote.response.UserRepositoryResponse
import com.submission.dicoding.dgitapp.databinding.ItemRepositoryBinding
import com.submission.dicoding.dgitapp.utils.OnRepositoryitemClickcallback
import com.submission.dicoding.dgitapp.utils.toShortNumberDisplay

class UserRepositoryAdapter(
    private val data: List<UserRepositoryResponse>,
    val callback: OnRepositoryitemClickcallback? = null
): RecyclerView.Adapter<UserRepositoryAdapter.UseHorizontalViewHolder>() {

    inner class UseHorizontalViewHolder(private val view: ItemRepositoryBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(data: UserRepositoryResponse) {
            view.apply {
                tvTitle.text = data.name
                tvDesc.text = data.description
                tvLanguage.text = data.language
                tvStarCount.text = data.starsCount.toShortNumberDisplay()
                tvWatchersCount.text = data.watchersCount.toShortNumberDisplay()
                tvForkCount.text = data.forksCount.toShortNumberDisplay()
            }
            itemView.setOnClickListener {
                callback?.onItemRepositoryClicked(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UseHorizontalViewHolder {
        val view = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UseHorizontalViewHolder(view)
    }

    override fun onBindViewHolder(holder: UseHorizontalViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}