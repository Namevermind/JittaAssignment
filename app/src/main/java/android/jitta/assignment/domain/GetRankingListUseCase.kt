package android.jitta.assignment.domain

import android.jitta.assignment.base.utils.ApiResponse
import android.jitta.assignment.data.MainRepository
import android.jitta.assignment.data.entities.RankingItem

class GetRankingListUseCase(
    private val repository: MainRepository
) {
    suspend operator fun invoke(params: Params): ApiResponse<List<RankingItem>> {
        return repository.getRankingList(
            market = params.market,
            page = params.page
        )
    }

    data class Params(
        val market: String = "TH",
        val page: Int = 0
    )
}