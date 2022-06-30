package com.camelsoft.ratetz.common.di

import android.content.Context
import androidx.room.Room
import com.camelsoft.ratetz._data.db.CurrencyDao
import com.camelsoft.ratetz._data.db.CurrencyDatabase
import com.camelsoft.ratetz._data.db.CurrencyDatabase.Companion.DATABASE_NAME
import com.camelsoft.ratetz._data.net.RateApi
import com.camelsoft.ratetz._data.repository.RateRepositoryImpl
import com.camelsoft.ratetz._data.net.addLoggingInterceptor
import com.camelsoft.ratetz._data.repository.RateRepository
import com.camelsoft.ratetz._data.repository.RateRepositoryMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideRateRepository(
        rateApi: RateApi,
        currencyDao: CurrencyDao,
        rateRepositoryMapper: RateRepositoryMapper
    ): RateRepository {
        return RateRepositoryImpl(rateApi, currencyDao, rateRepositoryMapper)
    }

    @Singleton
    @Provides
    fun provideCurrencyDatabase(@ApplicationContext context: Context): CurrencyDatabase {
        return Room
            .databaseBuilder(
                context,
                CurrencyDatabase::class.java,
                DATABASE_NAME,
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideCurrencyDao(currencyDatabase: CurrencyDatabase): CurrencyDao {
        return currencyDatabase.currencyDao()
    }

    @Singleton
    @Provides
    fun provideRateRepositoryMapper(): RateRepositoryMapper {
        return RateRepositoryMapper()
    }
}