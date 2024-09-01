package com.alex.themoviedb

import android.app.Application
import com.alex.themoviedb.di.domain
import com.alex.themoviedb.di.presentation
import com.alex.themoviedb.di.remote
import com.alex.themoviedb.di.repository
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(remote, repository, domain, presentation)
        }
    }
}