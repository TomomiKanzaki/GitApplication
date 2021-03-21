package com.example.gitapplication.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


class EndlessRecycler(
    layoutManager: RecyclerView.LayoutManager,
    private val onLoadMore: (page: Int, totalItemsCount: Int, view: RecyclerView) -> Unit
) :
    RecyclerView.OnScrollListener() {

    private var currentPage = 0
    private var visibleThreshold = 2
    private var mLayoutManager: RecyclerView.LayoutManager = layoutManager
    private var isDisableLoadMore = false
    private var isReloadWhenConnectNetwork = true

    var loading = false

    init {
        when (layoutManager) {
            is GridLayoutManager -> visibleThreshold *= layoutManager.spanCount
            is StaggeredGridLayoutManager -> visibleThreshold *= layoutManager.spanCount
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0 && !isDisableLoadMore && isReloadWhenConnectNetwork) {
            var lastVisibleItemPosition = 0
            val totalItemCount = mLayoutManager.itemCount

            when (mLayoutManager) {
                is StaggeredGridLayoutManager -> {
                    val lastVisibleItemPositions =
                        (mLayoutManager as StaggeredGridLayoutManager)
                            .findLastVisibleItemPositions(null)
                    lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
                }
                is GridLayoutManager -> lastVisibleItemPosition =
                    (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
                is LinearLayoutManager -> lastVisibleItemPosition =
                    (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            }

            if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount && dy > 0) {
                currentPage++
                loading = true
                onLoadMore.invoke(currentPage, totalItemCount, view)
            }
        }
    }

    fun setDisableLoadMore(isDisable: Boolean) {
        isDisableLoadMore = isDisable
    }
}
