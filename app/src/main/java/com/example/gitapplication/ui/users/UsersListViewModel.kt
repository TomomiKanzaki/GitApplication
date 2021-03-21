package com.example.gitapplication.ui.users

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitapplication.R
import com.example.gitapplication.data.model.Project
import com.example.gitapplication.data.model.User
import com.example.gitapplication.data.repository.GithubRepository
import com.example.gitapplication.util.DataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class UsersListViewModel() : ViewModel(), KoinComponent {

    private val repository: GithubRepository by inject()

    val isPullToRefresh = ObservableField<Boolean>()
    val isLoading = ObservableField<Boolean>()
    var usersListLiveData = MutableLiveData<MutableList<User>>()
    private var pageSize = 20

    init { loadUsersList() }

    fun loadUsersList(loadMore: Boolean = false) {
        if (loadMore) pageSize += 20
        repository.getUsersList(pageSize = pageSize) { response ->
            when (response.status) {
                DataResponse.Status.LOADING -> isLoading.set(true)
                DataResponse.Status.SUCCESS -> {
                    isLoading.set(false)
                    isPullToRefresh.set(false)
                    usersListLiveData.postValue(response.data as MutableList<User>?)
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
