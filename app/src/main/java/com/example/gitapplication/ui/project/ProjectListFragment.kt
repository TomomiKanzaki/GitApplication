package com.example.gitapplication.ui.project

import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gitapplication.R
import com.example.gitapplication.databinding.FragmentProjectListBinding
import com.example.gitapplication.util.addFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProjectListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object{
        private const val USER_ID = "userId"
        fun getInstance(userId: Int): ProjectListFragment{
            return ProjectListFragment().apply{
                this.arguments = bundleOf(USER_ID to userId)
            }
        }
    }

    private val viewModel: ProjectListViewModel by viewModel()

    private val userId: Int? by lazy {
        arguments?.getInt(USER_ID)
    }

    private lateinit var binding: FragmentProjectListBinding
    private lateinit var projectAdapter: ProjectAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_list, container, false)

        projectAdapter = ProjectAdapter(
            clickCallback = { projectId ->
                userId?.let {
                    addFragment(
                        R.id.fragment_container_project,
                        ProjectFragment.getInstance(it, projectId),
                        addFromActivity = true
                    )
                }
            }
        )

        binding.apply {
            refreshLayout.setOnRefreshListener(this@ProjectListFragment)
            projectList.adapter = projectAdapter
            projectAdapter.setEnableLoadMore(projectList){ _, _, _ ->
                userId?.let {
                    viewModel.loadProjectList(it)
                }
            }
            vm = viewModel
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userId?.let {
            viewModel.loadProjectList(it)
        }
        observeViewModel()
    }

    override fun onRefresh() {
        viewModel.isPullToRefresh.set(true)
        userId?.let { viewModel.loadProjectList(it) }
    }

    private fun observeViewModel() {
        viewModel.projectListLiveData.observe(viewLifecycleOwner, Observer { projects ->
            if (projects != null) {
                projectAdapter.disableLoadMore(false)
                projectAdapter.setProjectList(projects)
            }
        })
    }
}
