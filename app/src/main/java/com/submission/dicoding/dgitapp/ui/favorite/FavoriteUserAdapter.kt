package com.submission.dicoding.dgitapp.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.submission.dicoding.dgitapp.data.local.entity.FavoriteUserEntity
import com.submission.dicoding.dgitapp.databinding.ItemUserBinding
import com.submission.dicoding.dgitapp.utils.FavoriteUserShareCallback
import com.submission.dicoding.dgitapp.utils.OnFavoriteUserItemClickCallback
import com.submission.dicoding.dgitapp.utils.setImageUrl

class FavoriteUserAdapter(
    val callback: OnFavoriteUserItemClickCallback? = null,
    val listener: FavoriteUserShareCallback? = null
) :
    ListAdapter<FavoriteUserEntity, FavoriteUserAdapter.UseHorizontalViewHolder>(DIFF_CALLBACK) {

    inner class UseHorizontalViewHolder(private val view: ItemUserBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(data: FavoriteUserEntity) {
            view.apply {
                imgUser.setImageUrl(itemView.context, data.avatar, pbUser)
                tvUsername.text = data.username
                btnShare.setOnClickListener {
                    listener?.onFavoriteUserShareClick(data)
                }
            }
            itemView.setOnClickListener {
                callback?.onFavoriteUserItemClicked(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UseHorizontalViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UseHorizontalViewHolder(view)
    }

    override fun onBindViewHolder(holder: UseHorizontalViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) {
            holder.bind(user)
        }
    }

    fun getSwipedData(swipedPosition: Int): FavoriteUserEntity? = getItem(swipedPosition)

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<FavoriteUserEntity> =
            object : DiffUtil.ItemCallback<FavoriteUserEntity>() {
                override fun areItemsTheSame(
                    oldUser: FavoriteUserEntity,
                    newUser: FavoriteUserEntity
                ): Boolean {
                    return oldUser.userId == newUser.userId
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldUser: FavoriteUserEntity,
                    newUser: FavoriteUserEntity
                ): Boolean {
                    return oldUser == newUser
                }
            }
    }
}