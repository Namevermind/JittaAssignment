package android.jitta.assignment.data

import android.jitta.assignment.base.utils.ApiResponse
import android.jitta.assignment.data.entities.Country
import android.jitta.assignment.data.entities.Detail
import android.jitta.assignment.data.entities.RankingItem
import android.jitta.assignment.data.entities.Sector
import android.jitta.assignment.data.network.ApiGateway

interface MainRepository {
    suspend fun getCountry(): ApiResponse<List<Country>>
    suspend fun getSectors(): ApiResponse<List<Sector>>
    suspend fun getRankingList(
        market: String,
        sector: List<String>,
        page: Int
    ): ApiResponse<List<RankingItem>>

    suspend fun getDetailItem(id: String): ApiResponse<Detail>
}

class MainRepositoryImpl(private val api: ApiGateway) : MainRepository {

    override suspend fun getCountry(): ApiResponse<List<Country>> {
        return api.getCountryList()
    }

    override suspend fun getSectors(): ApiResponse<List<Sector>> {
        return api.getSectorList()
    }

    override suspend fun getRankingList(
        market: String,
        sector: List<String>,
        page: Int
    ): ApiResponse<List<RankingItem>> {
        return api.getRankingList(market = market, sector = sector, page = page)
    }

    override suspend fun getDetailItem(id: String): ApiResponse<Detail> {
        return api.getDetailItem(id)
    }
}