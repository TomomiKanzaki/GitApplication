package com.example.gitapplication.ui.users

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.gitapplication.R
import com.example.gitapplication.data.model.User
import com.example.gitapplication.databinding.ItemUsersListBinding
import com.example.gitapplication.util.EndlessRecycler
import com.example.gitapplication.util.runCardAnimationClickListener


class UsersAdapter(
    private val context: Context?,
    private var clickCallback: (id: Int) -> Unit?) :
        RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    private var usersList: MutableList<User>? = null

    private var loadMoreListener: EndlessRecycler? = null

    fun setUsersList(usersList: MutableList<User>) {
        if (this.usersList.isNullOrEmpty()) {
            this.usersList = usersList
            notifyItemRangeInserted(0, usersList.size)
        } else {
            loadMoreListener?.loading = false
            val insertPosition = usersList.size
            this.usersList = usersList
            notifyItemInserted(insertPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): UsersViewHolder {
        val binding =
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_users_list, parent,
                        false) as ItemUsersListBinding

        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.binding.apply {
            itemContent.runCardAnimationClickListener {
                usersList?.get(position)?.id?.let { id ->
                    clickCallback.invoke(id)
                }
            }
            if (context != null) {
                Glide.with(context)
                    .load(usersList?.get(position)?.avatar_url?: "")
                    .listener(object: RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            return false
                        }
                    }).into(ivAvatar)
            }

            user = usersList?.get(position)
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return usersList?.size ?: 0
    }


    fun setEnableLoadMore(recyclerView: RecyclerView, onLoadMoreListener: (page: Int, totalItemsCount: Int, view: RecyclerView) -> Unit) {
        recyclerView.layoutManager?.let {
            loadMoreListener = EndlessRecycler(it) { page: Int, totalItemsCount: Int, view: RecyclerView ->
                recyclerView.post {
                    showLoadMore()
                }
                onLoadMoreListener.invoke(page, totalItemsCount, view)
            }
            recyclerView.addOnScrollListener(loadMoreListener!!)
        }
    }

    fun disableLoadMore(isDisable: Boolean) {
        loadMoreListener?.setDisableLoadMore(isDisable)
    }

    private fun showLoadMore() {
        notifyItemInserted(usersList?.count()?:1 - 1)
        loadMoreListener?.loading = true
    }

    open class UsersViewHolder(val binding: ItemUsersListBinding) : RecyclerView.ViewHolder(binding.root)
}
