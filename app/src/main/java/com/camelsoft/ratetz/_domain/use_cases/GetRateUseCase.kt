package com.camelsoft.ratetz._domain.use_cases

import com.camelsoft.ratetz.R
import com.camelsoft.ratetz._data.net.toRate
import com.camelsoft.ratetz._domain.models.Rate
import com.camelsoft.ratetz._domain.repository.RateRepository
import com.camelsoft.ratetz.common.App.Companion.getAppContext
import com.camelsoft.ratetz.common.events.EventsAsync
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRateUseCase @Inject constructor(
    private val repository: RateRepository
) {
    operator fun invoke(): Flow<EventsAsync<Rate>> = flow {
        try {
            emit(EventsAsync.Loading<Rate>())
            val rate = repository.getRate().toRate()
            emit(EventsAsync.Success<Rate>(rate))
        } catch(e: HttpException) {
            emit(EventsAsync.Error<Rate>(e.localizedMessage ?: getAppContext().resources.getString(R.string.error_text_unknown)))
        } catch(e: IOException) {
            emit(EventsAsync.Error<Rate>(getAppContext().resources.getString(R.string.internet_lose)))
        }
    }
}