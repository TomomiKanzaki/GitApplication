package com.example.gitapplication.di

import com.example.gitapplication.ui.project.ProjectListViewModel
import com.example.gitapplication.ui.project.ProjectViewModel
import com.example.gitapplication.ui.users.UsersListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Every ViewModel should define here to inject dependency on other class
 * https://insert-koin.io/docs/2.0/getting-started/android-viewmodel/
 */
val viewModelModule = module {

    viewModel {
        UsersListViewModel()
    }
    viewModel {
        ProjectListViewModel()
    }
    viewModel {
        ProjectViewModel()
    }

}
