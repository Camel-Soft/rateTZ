package com.camelsoft.ratetz._domain.use_cases

import com.camelsoft.ratetz.R
import com.camelsoft.ratetz._data.net.toRate
import com.camelsoft.ratetz._domain.models.MRate
import com.camelsoft.ratetz._domain.repository.RateRepository
import com.camelsoft.ratetz.common.App
import com.camelsoft.ratetz.common.state.StateAsync
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRateByBaseUseCase @Inject constructor(
    private val repository: RateRepository
) {
    operator fun invoke(base: String): Flow<StateAsync<MRate>> = flow {
        try {
            emit(StateAsync.Loading<MRate>())
            val rate = repository.getRateByBase(base).toRate()
            emit(StateAsync.Success<MRate>(rate))
        } catch(e: HttpException) {
            emit(
                StateAsync.Error<MRate>(e.localizedMessage ?: App.getAppContext().resources.getString(
                    R.string.error_text_unknown)))
        } catch(e: IOException) {
            emit(StateAsync.Error<MRate>(App.getAppContext().resources.getString(R.string.internet_lose)))
        }
    }
}