package com.example.topnews.ui.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DatabaseModule::class,
        FactoryModule::class,
        LocalDataSourceModule::class,
        NetModule::class,
        RemoteDataSourceModule::class,
        RepositoryModule::class,
        UseCaseModule::class
    ]
)
interface AppComponent {
}