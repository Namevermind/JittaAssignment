package android.jitta.assignment.data.network

import android.jitta.assignment.AvailableCountryQuery
import android.jitta.assignment.JittaRankingQuery
import android.jitta.assignment.ListSectorTypeQuery
import android.jitta.assignment.base.utils.ApiException
import android.jitta.assignment.base.utils.ApiResponse
import android.jitta.assignment.data.entities.Country
import android.jitta.assignment.data.entities.RankingItem
import android.jitta.assignment.data.entities.Sector
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Error

interface ApiGateway {
    suspend fun getCountryList(): ApiResponse<List<Country>>
    suspend fun getSectorList(): ApiResponse<List<Sector>>
    suspend fun getRankingList(market: String, page: Int): ApiResponse<List<RankingItem>>
}

class ApiGatewayImpl(
    private val apolloClient: ApolloClient
) : ApiGateway {

    private fun Error?.toApiException(): ApiResponse.Failure {
        return ApiResponse.Failure(ApiException(this?.message))
    }

    override suspend fun getCountryList(): ApiResponse<List<Country>> {
        val response = apolloClient.query(AvailableCountryQuery()).execute()
        if (response.hasErrors()) return response.errors?.first().toApiException()

        val result = response.data?.availableCountry.orEmpty()
        return ApiResponse.Success(result.map {
            Country(
                code = it?.code.orEmpty(),
                name = it?.name.orEmpty(),
                flag = it?.flag.orEmpty()
            )
        })

    }

    override suspend fun getSectorList(): ApiResponse<List<Sector>> {
        val response = apolloClient.query(ListSectorTypeQuery()).execute()
        if (response.hasErrors()) return response.errors?.first().toApiException()

        val result = response.data?.listJittaSectorType.orEmpty()
        return ApiResponse.Success(result.map {
            Sector(
                id = it?.id.orEmpty(),
                name = it?.name.orEmpty()
            )
        })
    }

    override suspend fun getRankingList(
        market: String,
        page: Int
    ): ApiResponse<List<RankingItem>> {
        val response = apolloClient.query(JittaRankingQuery(market = market, page = page)).execute()
        if (response.hasErrors()) return response.errors?.first().toApiException()

        val result = response.data?.jittaRanking
        val count = result?.count ?: 0
        val data = result?.data ?: emptyList()
        return ApiResponse.Success(data.map { item ->
            RankingItem(
                id = item?.id.orEmpty(),
                symbol = item?.symbol.orEmpty(),
                title = item?.title.orEmpty(),
                rank = item?.rank ?: 0,
                count = count,
                sectorId = item?.sector?.id.orEmpty()
            )
        })

    }
}

