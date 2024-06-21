package com.aula6

import android.app.Application
import com.aula6.di.androidDataModule
import di.dataModule
import di.networkModule
import di.screenModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(
                androidDataModule,
                networkModule,
                dataModule,
                screenModelModule
            )
        }
    }
}