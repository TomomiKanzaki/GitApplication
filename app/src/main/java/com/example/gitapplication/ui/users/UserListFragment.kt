package com.example.gitapplication.ui.users

import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gitapplication.R
import com.example.gitapplication.databinding.FragmentUsersListBinding
import com.example.gitapplication.ui.project.ProjectListFragment
import com.example.gitapplication.util.addFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener{

    private val viewModel: UsersListViewModel by viewModel()

    private lateinit var binding: FragmentUsersListBinding
    private lateinit var userAdapter: UsersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_users_list, container, false)

        userAdapter = UsersAdapter(
            context,
            clickCallback = {
                addFragment(
                    R.id.fragment_container,
                    ProjectListFragment.getInstance(it),
                    addFromActivity = true
                )
            }
        )

        binding.apply {
            refreshLayout.setOnRefreshListener(this@UsersListFragment)
            vm = viewModel
            usersList.adapter = userAdapter
            userAdapter.setEnableLoadMore(usersList){ _, _, _ ->
                viewModel.loadUsersList(true)
            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeViewModel()
    }

    override fun onRefresh() {
        viewModel.isPullToRefresh.set(true)
        viewModel.loadUsersList()
    }

    private fun observeViewModel() {
        viewModel.usersListLiveData.observe(viewLifecycleOwner, Observer { users ->
            userAdapter.disableLoadMore(false)
            userAdapter.setUsersList(users)
        })
    }
}
