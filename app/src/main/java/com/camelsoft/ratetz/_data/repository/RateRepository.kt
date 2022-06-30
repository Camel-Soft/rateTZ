package com.camelsoft.ratetz._data.repository

import com.camelsoft.ratetz._domain.models.MRate

interface RateRepository {
    suspend fun getRate(): MRate
    suspend fun getRateByBase(base: String): MRate
    suspend fun insertCurrency(currency: String)
    suspend fun deleteCurrency(currency: String)
}