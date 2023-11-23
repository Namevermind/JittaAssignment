package android.jitta.assignment.data.network

import android.jitta.assignment.AvailableCountryQuery
import android.jitta.assignment.JittaRankingQuery
import android.jitta.assignment.ListSectorTypeQuery
import android.jitta.assignment.StockSummaryQuery
import android.jitta.assignment.base.utils.ApiException
import android.jitta.assignment.base.utils.ApiResponse
import android.jitta.assignment.data.entities.Country
import android.jitta.assignment.data.entities.Detail
import android.jitta.assignment.data.entities.Detail.Factor
import android.jitta.assignment.data.entities.Detail.Factor.Item
import android.jitta.assignment.data.entities.Detail.Sign
import android.jitta.assignment.data.entities.RankingItem
import android.jitta.assignment.data.entities.Sector
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Error

interface ApiGateway {
    suspend fun getCountryList(): ApiResponse<List<Country>>
    suspend fun getSectorList(): ApiResponse<List<Sector>>
    suspend fun getRankingList(
        market: String,
        sector: List<String>,
        page: Int
    ): ApiResponse<List<RankingItem>>

    suspend fun getDetailItem(id: String): ApiResponse<Detail>
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
                code = it?.code,
                name = it?.name,
                flag = it?.flag
            )
        })

    }

    override suspend fun getSectorList(): ApiResponse<List<Sector>> {
        val response = apolloClient.query(ListSectorTypeQuery()).execute()
        if (response.hasErrors()) return response.errors?.first().toApiException()

        val result = response.data?.listJittaSectorType.orEmpty()
        return ApiResponse.Success(result.map {
            Sector(
                id = it?.id,
                name = it?.name
            )
        })
    }

    override suspend fun getRankingList(
        market: String,
        sector: List<String>,
        page: Int
    ): ApiResponse<List<RankingItem>> {
        val response =
            apolloClient.query(JittaRankingQuery(market = market, sectors = sector, page = page))
                .execute()
        if (response.hasErrors()) return response.errors?.first().toApiException()

        val result = response.data?.jittaRanking
        val count = result?.count
        val data = result?.data ?: emptyList()
        return ApiResponse.Success(data.map { item ->
            RankingItem(
                id = item?.id,
                symbol = item?.symbol,
                title = item?.title,
                rank = item?.rank ?: 0,
                count = count,
                sectorId = item?.sector?.id
            )
        })

    }

    override suspend fun getDetailItem(id: String): ApiResponse<Detail> {
        val response = apolloClient.query(StockSummaryQuery(id = id)).execute()
        if (response.hasErrors()) return response.errors?.first().toApiException()

        val result = response.data?.stock
        val jitta = result?.jitta
        val diff = jitta?.priceDiff?.last?.let { diff ->
            Detail.Diff(
                value = diff.value,
                type = diff.onPriceDiffItem?.type,
                percentage = diff.onPriceDiffItem?.percent
            )
        }
        val factor = jitta?.factor?.last?.value.let { factor ->
            Factor(
                growthItem = factor?.growth?.let { Item(it.name, it.level, it.value) },
                recentItem = factor?.recent?.let { Item(it.name, it.level, it.value) },
                financialItem = factor?.financial?.let { Item(it.name, it.level, it.value) },
                returnItem = factor?.`return`?.let { Item(it.name, it.level, it.value) },
                managementItem = factor?.management?.let {
                    Item(it.name, it.level, it.value)
                },
            )
        }

        return ApiResponse.Success(
            Detail(
                title = result?.title,
                id = result?.id,
                rank = result?.comparison?.market?.rank,
                member = result?.comparison?.market?.member,
                score = jitta?.score?.last?.value,
                currentDate = result?.price?.latest?.datetime.toString(),
                currentPrice = result?.price?.latest?.close,
                diff = diff,
                factor = factor,
                sign = jitta?.sign?.last?.map { Sign(it?.title, it?.type, it?.value) },
                description = result?.summary,
                sectorName = result?.sector?.name,
                industry = result?.industry,
                website = result?.company?.link?.firstOrNull()?.url
            )
        )
    }
}

