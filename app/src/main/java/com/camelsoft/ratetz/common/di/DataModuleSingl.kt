package com.camelsoft.ratetz.common.di

import com.camelsoft.ratetz._data.net.RateApi
import com.camelsoft.ratetz._data.net.RateRepositoryImpl
import com.camelsoft.ratetz._data.net.addLoggingInterceptor
import com.camelsoft.ratetz._domain.repository.RateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModuleSingl {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addLoggingInterceptor().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
            .baseUrl(RateApi.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRateApi(retrofit: Retrofit): RateApi {
        return retrofit.create(RateApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRateRepository(rateApi: RateApi): RateRepository {
        return RateRepositoryImpl(rateApi)
    }
}