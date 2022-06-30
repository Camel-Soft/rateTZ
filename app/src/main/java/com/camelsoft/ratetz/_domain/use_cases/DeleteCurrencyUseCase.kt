package com.camelsoft.ratetz._domain.use_cases

import com.camelsoft.ratetz._data.repository.RateRepository
import javax.inject.Inject

class DeleteCurrencyUseCase @Inject constructor(
    private val repository: RateRepository
) {
    suspend operator fun invoke(currency: String) {
        repository.deleteCurrency(currency = currency)
    }
}