package di

import data.DatabaseDriverFactory
import data.IOSDatabaseDriverFactory
import org.koin.dsl.module

val iosDataModule = module {
    single <DatabaseDriverFactory>{ IOSDatabaseDriverFactory() }
}