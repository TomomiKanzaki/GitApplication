package com.example.gitapplication.di

import com.example.gitapplication.data.repository.GithubRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory { GithubRepository(get()) }

}