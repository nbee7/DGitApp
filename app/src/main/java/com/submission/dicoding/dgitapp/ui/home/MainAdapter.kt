package com.submission.dicoding.dgitapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.submission.dicoding.dgitapp.data.UserEntity
import com.submission.dicoding.dgitapp.databinding.ItemUserBinding
import com.submission.dicoding.dgitapp.utils.OnUserItemClickCallback
import com.submission.dicoding.dgitapp.utils.ShareCallback

class MainAdapter(private val data: ArrayList<UserEntity>, val callback: OnUserItemClickCallback? = null, val listener: ShareCallback? = null): RecyclerView.Adapter<MainAdapter.UseHorizontalViewHolder>() {
    inner class UseHorizontalViewHolder(private val view: ItemUserBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(data: UserEntity) {
            val avatarSource: Int = itemView.context.resources.getIdentifier(data.avatar, null, itemView.context.packageName)
            view.apply {
                tvUsername.text = data.username
                btnShare.setOnClickListener{
                    listener?.onShareClick(data)
                }
            }

            Glide.with(itemView.context)
                .load(avatarSource)
                .apply(RequestOptions.circleCropTransform())
                .into(view.imgUser)

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