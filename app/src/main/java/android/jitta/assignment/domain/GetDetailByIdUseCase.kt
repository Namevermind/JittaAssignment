package android.jitta.assignment.domain

import android.jitta.assignment.base.utils.ApiResponse
import android.jitta.assignment.data.MainRepository
import android.jitta.assignment.data.entities.Detail

class GetDetailByIdUseCase(
    private val repository: MainRepository
) {

    suspend operator fun invoke(params: Params): ApiResponse<Detail> {
        return repository.getDetailItem(params.id)
    }

    data class Params(
        val id: String
    )
}