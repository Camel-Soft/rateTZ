package com.camelsoft.ratetz._data.repository

import com.camelsoft.ratetz._data.db.CurrencyEntity
import com.camelsoft.ratetz._data.net.RateDto
import com.camelsoft.ratetz._domain.models.MRate
import com.camelsoft.ratetz._domain.models.MRateRv
import com.camelsoft.ratetz._domain.utils.floatToString

class RateRepositoryMapper {
    fun mapRates(listCurrencyEntity: List<CurrencyEntity>, rateDto: RateDto): MRate {
        val listRateRv = mutableListOf<MRateRv>()
        rateDto.rates.map { rate ->
            listRateRv.add(
                MRateRv(
                    name = rate.key,
                    rate = floatToString(rate.value, 4, '.'),
                    isSelected = listCurrencyEntity.find { currencyEntity ->
                        currencyEntity.currency == rate.key } != null
            ))
        }
        return MRate(
            amount = rateDto.amount,
            base = rateDto.base,
            date = rateDto.date,
            rates = listRateRv
        )
    }
}