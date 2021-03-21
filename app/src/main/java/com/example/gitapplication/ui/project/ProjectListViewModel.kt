package com.example.gitapplication.ui.project

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitapplication.data.model.Project
import com.example.gitapplication.data.repository.GithubRepository
import com.example.gitapplication.util.DataResponse
import org.koin.core.KoinComponent
import org.koin.core.inject

class ProjectListViewModel() : ViewModel(), KoinComponent {

    private val repository: GithubRepository by inject()

    val isPullToRefresh = ObservableField<Boolean>()
    val isLoading = ObservableField<Boolean>()
    private var pageSize = 20

    var projectListLiveData: MutableLiveData<List<Project>> = MutableLiveData()

    fun loadProjectList(userId: Int, loadMore: Boolean = false) {
        if (loadMore) pageSize += 20
        repository.getProjectList(userId, pageSize = pageSize){response ->
            when(response.status){
                DataResponse.Status.LOADING -> isLoading.set(true)
                DataResponse.Status.SUCCESS -> {
                    isPullToRefresh.set(false)
                    isLoading.set(false)
                    projectListLiveData.postValue(response.data)
                }
                DataResponse.Status.ERROR -> {
                    isPullToRefresh.set(false)
                    isLoading.set(false)
                    Log.e("error", "loadProjectList: ${response.error}")
                }
            }
        }
    }
}
