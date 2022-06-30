package com.camelsoft.ratetz.common.di

import com.camelsoft.ratetz._data.repository.RateRepository
import com.camelsoft.ratetz._domain.use_cases.GetRateByBaseUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DataModuleVm {

    @Provides
    @ViewModelScoped
    fun provideGetRateByBaseUseCase(rateRepository: RateRepository): GetRateByBaseUseCase {
        return GetRateByBaseUseCase(rateRepository)
    }
}