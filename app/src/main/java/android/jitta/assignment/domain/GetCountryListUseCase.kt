package android.jitta.assignment.domain

import android.jitta.assignment.base.utils.ApiResponse
import android.jitta.assignment.data.MainRepository
import android.jitta.assignment.data.entities.Country

class GetCountryListUseCase(
    private val repository: MainRepository
) {
    suspend operator fun invoke(): ApiResponse<List<Country>> {
        return repository.getCountry()
    }
}