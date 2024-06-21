package com.aula6.di

import com.aula6.data.AndroidDatabaseDriverFactory
import data.DatabaseDriverFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val androidDataModule = module {
    single <DatabaseDriverFactory>{ AndroidDatabaseDriverFactory(androidApplication()) }
}