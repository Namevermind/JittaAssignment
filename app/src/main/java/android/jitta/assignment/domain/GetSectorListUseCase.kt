package android.jitta.assignment.domain

import android.jitta.assignment.base.utils.ApiResponse
import android.jitta.assignment.data.MainRepository
import android.jitta.assignment.data.entities.Sector

class GetSectorListUseCase(
    private val repository: MainRepository
) {
    suspend operator fun invoke(): ApiResponse<List<Sector>> {
        return repository.getSectors()
    }
}