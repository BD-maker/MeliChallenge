package com.brunodiaz.melichallenge.di

import com.brunodiaz.melichallenge.data.network.MeliAPI
import com.brunodiaz.melichallenge.data.repository.DetailRepository
import com.brunodiaz.melichallenge.data.repository.DetailRepositoryImpl
import com.brunodiaz.melichallenge.data.repository.ResultsRepository
import com.brunodiaz.melichallenge.data.repository.ResultsRepositoryImpl
import com.brunodiaz.melichallenge.domain.GetItemDetailUseCase
import com.brunodiaz.melichallenge.domain.GetItemDetailUseCaseImpl
import com.brunodiaz.melichallenge.domain.GetItemListUseCaseImpl
import com.brunodiaz.melichallenge.domain.GetItemsListUseCase
import com.brunodiaz.melichallenge.other.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)  // ApplicationComponent
class AppModule {

    @Singleton
    @Provides
    fun provideMeliAPI(): MeliAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MeliAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideResultRepository(
        api: MeliAPI,
    ) = ResultsRepositoryImpl(api) as ResultsRepository

    @Singleton
    @Provides
    fun provideDetailRepository(
        api: MeliAPI,
    ) = DetailRepositoryImpl(api) as DetailRepository

    @Singleton
    @Provides
    fun provideGetItemsListUseCase(
        repository: ResultsRepository,
    ) = GetItemListUseCaseImpl(repository) as GetItemsListUseCase

    @Singleton
    @Provides
    fun provideGetDetailUseCase(
        repository: DetailRepository,
    ) = GetItemDetailUseCaseImpl(repository) as GetItemDetailUseCase


}