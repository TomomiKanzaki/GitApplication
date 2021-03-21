package com.example.gitapplication.ui.project

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gitapplication.R
import com.example.gitapplication.databinding.FragmentProjectBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProjectFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        private const val USER_ID = "userId"
        private const val PROJECT_ID = "project_id"
        fun getInstance(userId: Int, projectId: String): ProjectFragment{
            return ProjectFragment().apply{
                this.arguments = bundleOf(
                    USER_ID to userId,
                    PROJECT_ID to projectId
                )
            }
        }
    }

    private val userId: Int? by lazy { arguments?.getInt(USER_ID) }
    private val projectID by lazy { arguments?.getString(PROJECT_ID) }

    private lateinit var binding: FragmentProjectBinding

    private val viewModel: ProjectViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.apply {
            vm = viewModel
            refreshLayout.setOnRefreshListener(this@ProjectFragment)
            this.lifecycleOwner = this@ProjectFragment
        }
        userId?.let { userId ->
            projectID?.let { projectId ->
                viewModel.isLoading.set(true)
                viewModel.loadProject(userId, projectId)
            }
        }
        observeViewModel()

    }

    private fun observeViewModel() {
    }

    override fun onRefresh() {
        userId?.let { userId ->
            projectID?.let { projectId ->
                viewModel.isPullToRefresh.set(true)
                viewModel.loadProject(userId, projectId)
            }
        }
    }
}
