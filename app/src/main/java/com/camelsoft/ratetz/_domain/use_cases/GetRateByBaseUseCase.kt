package com.camelsoft.ratetz._domain.use_cases

import com.camelsoft.ratetz.R
import com.camelsoft.ratetz._data.net.toRate
import com.camelsoft.ratetz._domain.models.Rate
import com.camelsoft.ratetz._domain.repository.RateRepository
import com.camelsoft.ratetz.common.App
import com.camelsoft.ratetz.common.events.EventsAsync
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRateByBaseUseCase @Inject constructor(
    private val repository: RateRepository
) {
    operator fun invoke(base: String): Flow<EventsAsync<Rate>> = flow {
        try {
            emit(EventsAsync.Loading<Rate>())
            val rate = repository.getRateByBase(base).toRate()
            emit(EventsAsync.Success<Rate>(rate))
        } catch(e: HttpException) {
            emit(
                EventsAsync.Error<Rate>(e.localizedMessage ?: App.getAppContext().resources.getString(
                    R.string.error_text_unknown)))
        } catch(e: IOException) {
            emit(EventsAsync.Error<Rate>(App.getAppContext().resources.getString(R.string.internet_lose)))
        }
    }
}