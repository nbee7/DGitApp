package com.submission.dicoding.dgitapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.submission.dicoding.dgitapp.data.remote.response.UserItems
import com.submission.dicoding.dgitapp.databinding.ItemUserBinding
import com.submission.dicoding.dgitapp.utils.OnUserItemClickCallback
import com.submission.dicoding.dgitapp.utils.ShareCallback
import com.submission.dicoding.dgitapp.utils.setImageUrl

class MainAdapter(
    private val data: List<UserItems>,
    val callback: OnUserItemClickCallback? = null,
    val listener: ShareCallback? = null
) : RecyclerView.Adapter<MainAdapter.UseHorizontalViewHolder>() {
    inner class UseHorizontalViewHolder(private val view: ItemUserBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(data: UserItems) {
            view.apply {
                imgUser.setImageUrl(itemView.context, data.avatarUrl, pbUser)
                tvUsername.text = data.login
                btnShare.setOnClickListener {
                    listener?.onShareClick(data)
                }
            }
            itemView.setOnClickListener {
                callback?.onUserItemClicked(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UseHorizontalViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UseHorizontalViewHolder(view)
    }

    override fun onBindViewHolder(holder: UseHorizontalViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}