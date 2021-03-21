package com.example.gitapplication.ui.project

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.gitapplication.R
import com.example.gitapplication.data.model.Project
import com.example.gitapplication.databinding.ItemProjectListBinding
import com.example.gitapplication.util.EndlessRecycler
import com.example.gitapplication.util.runCardAnimationClickListener

class ProjectAdapter(private var clickCallback: (projectId: String) -> Unit?) :
        RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    private var projectList: List<Project>? = null

    private var loadMoreListener: EndlessRecycler? = null

    fun setProjectList(projectList: List<Project>) {

        if (this.projectList.isNullOrEmpty()) {
            this.projectList = projectList
            notifyItemRangeInserted(0, projectList.size)
        } else {
            val insertPosition = projectList.size
            this.projectList = projectList
            notifyItemInserted(insertPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): ProjectViewHolder {
        val binding =
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_project_list, parent,
                        false) as ItemProjectListBinding

        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.binding.apply {
            itemContent.runCardAnimationClickListener {
                projectList?.get(position)?.let {
                    clickCallback.invoke(it.name)
                }
            }
            project = projectList?.get(position)
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return projectList?.size ?: 0
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
        notifyItemInserted(projectList?.count()?:1 - 1)
        loadMoreListener?.loading = true
    }

    open class ProjectViewHolder(val binding: ItemProjectListBinding) : RecyclerView.ViewHolder(binding.root)
}
