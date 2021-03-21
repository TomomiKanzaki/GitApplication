package com.example.gitapplication.ui.project

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.gitapplication.data.model.Project
import com.example.gitapplication.data.repository.GithubRepository
import com.example.gitapplication.util.DataResponse

import org.koin.core.KoinComponent
import org.koin.core.inject

class ProjectViewModel() :  ViewModel(), KoinComponent {

    private val repository: GithubRepository by inject()

    val isPullToRefresh = ObservableField<Boolean>()
    val isLoading = ObservableField<Boolean>()

    val projectLiveData = MutableLiveData<Project>()

    fun loadProject(userId: Int, projectName: String) {
        repository.getProject(userId, projectName) { response ->
            when (response.status) {
                DataResponse.Status.LOADING -> isLoading.set(true)
                DataResponse.Status.SUCCESS -> {
                    isPullToRefresh.set(false)
                    isLoading.set(false)
                    projectLiveData.postValue(response.data)
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
