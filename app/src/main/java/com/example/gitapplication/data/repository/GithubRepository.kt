package com.example.gitapplication.data.repository

import com.example.gitapplication.data.GithubService
import com.example.gitapplication.data.model.Project
import com.example.gitapplication.data.model.User
import com.example.gitapplication.util.DataResponse
import com.example.gitapplication.util.callApiDefault
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinComponent

class GithubRepository(private val githubService: GithubService) : KoinComponent {

    fun getUsersList(pageSize: Int, response: (response: DataResponse<List<User>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).callApiDefault(response) {
            githubService.getUsersList(per_page = pageSize)
        }
    }

    fun getProjectList(userId: Int, pageSize: Int, response: (response: DataResponse<List<Project>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).callApiDefault(response) {
            githubService.getProjectList(userId, pageSize)
        }
    }

    fun getProject(userId: Int, projectName: String, response: (response: DataResponse<Project>) -> Unit) {
        CoroutineScope(Dispatchers.IO).callApiDefault(response) {
            githubService.getProject(userId, projectName)
        }
    }
}