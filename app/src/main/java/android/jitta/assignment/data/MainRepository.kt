package android.jitta.assignment.data

import android.jitta.assignment.base.utils.ApiResponse
import android.jitta.assignment.data.entities.Country
import android.jitta.assignment.data.entities.Sector
import android.jitta.assignment.data.network.ApiGateway

interface MainRepository {
    suspend fun getCountrys(): ApiResponse<List<Country>>
    suspend fun getSectors(): ApiResponse<List<Sector>>
}

class MainRepositoryImpl(private val api: ApiGateway) : MainRepository {

    override suspend fun getCountrys(): ApiResponse<List<Country>> {
        return api.getCountryList()
    }

    override suspend fun getSectors(): ApiResponse<List<Sector>> {
        return api.getSectorList()
    }
}