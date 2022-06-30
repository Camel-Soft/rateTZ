package com.camelsoft.ratetz._data.repository

import com.camelsoft.ratetz._data.db.CurrencyDao
import com.camelsoft.ratetz._data.db.CurrencyEntity
import com.camelsoft.ratetz._data.net.RateApi
import com.camelsoft.ratetz._domain.models.MRate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RateRepositoryImpl @Inject constructor(
    private val rateApi: RateApi,
    private val currencyDao: CurrencyDao,
    private val rateRepositoryMapper: RateRepositoryMapper
): RateRepository {

    override suspend fun getRate(): MRate {
        return withContext(Dispatchers.IO) {
            val rateJob = async {
                return@async rateApi.getRate()
            }
            val currencyJob = async {
                return@async currencyDao.getAll()
            }
            return@withContext rateRepositoryMapper.mapRates(currencyJob.await(), rateJob.await())
        }
    }

    override suspend fun getRateByBase(base: String): MRate {
        return withContext(Dispatchers.IO) {
            val rateJob = async {
                return@async rateApi.getRateByBase(base)
            }
            val currencyJob = async {
                return@async currencyDao.getAll()
            }
            return@withContext rateRepositoryMapper.mapRates(currencyJob.await(), rateJob.await())
        }
    }

    override suspend fun insertCurrency(currency: String) {
        withContext(Dispatchers.IO) {
            currencyDao.insert(CurrencyEntity(currency = currency))
        }
    }

    override suspend fun deleteCurrency(currency: String) {
        withContext(Dispatchers.IO) {
            currencyDao.delete(CurrencyEntity(currency = currency))
        }
    }
}