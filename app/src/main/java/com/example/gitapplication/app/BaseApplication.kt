package com.example.gitapplication.app

import android.app.Application
import com.example.gitapplication.di.appModule
import com.example.gitapplication.di.netModule
import com.example.gitapplication.di.repositoryModule
import com.example.gitapplication.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(listOf(appModule, netModule, viewModelModule, repositoryModule))
        }
    }

}