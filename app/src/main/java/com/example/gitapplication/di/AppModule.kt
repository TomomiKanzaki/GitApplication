package com.example.gitapplication.di

import com.example.gitapplication.app.BaseApplication
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single { androidApplication() as BaseApplication }
}
