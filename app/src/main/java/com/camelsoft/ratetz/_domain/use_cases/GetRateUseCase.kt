package com.camelsoft.ratetz._domain.use_cases

import com.camelsoft.ratetz.R
import com.camelsoft.ratetz._domain.models.MRate
import com.camelsoft.ratetz._data.repository.RateRepository
import com.camelsoft.ratetz.common.App.Companion.getAppContext
import com.camelsoft.ratetz.common.state.StateAsync
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRateUseCase @Inject constructor(
    private val repository: RateRepository
) {
    operator fun invoke(): Flow<StateAsync<MRate>> = flow {
        try {
            emit(StateAsync.Loading())
            val rate = repository.getRate()
            emit(StateAsync.Success(rate))
        } catch(e: HttpException) {
            emit(StateAsync.Error(e.localizedMessage ?: getAppContext().resources.getString(R.string.error_text_unknown)))
        } catch(e: IOException) {
            emit(StateAsync.Error(getAppContext().resources.getString(R.string.internet_lose)))
        }
    }
}