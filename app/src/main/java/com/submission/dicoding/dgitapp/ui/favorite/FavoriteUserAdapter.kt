package com.submission.dicoding.dgitapp.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.submission.dicoding.dgitapp.data.local.entity.FavoriteUserEntity
import com.submission.dicoding.dgitapp.databinding.ItemUserBinding
import com.submission.dicoding.dgitapp.utils.FavoriteUserShareCallback
import com.submission.dicoding.dgitapp.utils.OnFavoriteUserItemClickCallback
import com.submission.dicoding.dgitapp.utils.setImageUrl

class FavoriteUserAdapter(
    private val data: List<FavoriteUserEntity>,
    val callback: OnFavoriteUserItemClickCallback? = null,
    val listener: FavoriteUserShareCallback? = null
) :
    RecyclerView.Adapter<FavoriteUserAdapter.UseHorizontalViewHolder>() {

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
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}