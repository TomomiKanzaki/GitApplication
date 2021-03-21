package com.example.gitapplication.data

import com.example.gitapplication.data.model.Project
import com.example.gitapplication.data.model.User
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET("users")
    fun getUsersList(@Query("per_page") per_page: Int): Deferred<List<User>>

    @GET("users/{user}/repos")
    fun getProjectList(@Path("user")user: Int, @Query("per_page") per_page: Int): Deferred<List<Project>>

    @GET("/repos/{user}/{reponame}")
    fun getProject(@Path("user") user: Int, @Path("reponame") projectName: String): Deferred<Project>

}