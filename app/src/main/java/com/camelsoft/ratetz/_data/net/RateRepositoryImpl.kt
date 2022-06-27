package com.camelsoft.ratetz._data.net

import com.camelsoft.ratetz._domain.repository.RateRepository
import javax.inject.Inject

class RateRepositoryImpl @Inject constructor(
    private val rateApi: RateApi
): RateRepository {

    override suspend fun getRate(): RateDto {
        return rateApi.getRate()
    }

    override suspend fun getRateByBase(base: String): RateDto {
        return rateApi.getRateByBase(base)
    }
}