package com.submission.dicoding.dgitapp

import android.app.Application
import com.submission.dicoding.dgitapp.di.databaseModule
import com.submission.dicoding.dgitapp.di.networkModule
import com.submission.dicoding.dgitapp.di.repositoryModule
import com.submission.dicoding.dgitapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}