package android.jitta.assignment.ui.main

import android.jitta.assignment.base.utils.ApiResponse
import android.jitta.assignment.base.utils.MutableLiveEvent
import android.jitta.assignment.data.entities.Country
import android.jitta.assignment.data.entities.RankingItem
import android.jitta.assignment.data.entities.Sector
import android.jitta.assignment.domain.GetCountryListUseCase
import android.jitta.assignment.domain.GetRankingListUseCase
import android.jitta.assignment.domain.GetSectorListUseCase
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(
    private val getCountryListUseCase: GetCountryListUseCase,
    private val getSectorListUseCase: GetSectorListUseCase,
    private val getRankingListUseCase: GetRankingListUseCase
) : ViewModel() {

    var currentPage = 0

    val countryList = MutableLiveData<List<Country>>()
    val sectorList = MutableLiveData<List<Sector>>()
    val rankingList = MutableLiveData<List<RankingItem>>()

    val onLoadingInitSuccess = MutableLiveEvent<Unit>()

    val currentMarketType = MutableLiveData<String>()
    val marketTypeDisplay = MutableLiveData<String>()
    val sectorTypeDisplay = MutableLiveData<String>()

    fun initialize() {
        currentPage = 0
        viewModelScope.launch {
            countryList.value = getCountryList()
            sectorList.value = getSectorTypeList()

            // setup init value
            countryList.value?.find { it.code == "TH" }?.also {
                marketTypeDisplay.value = it.name
                currentMarketType.value = it.code
            }
            sectorTypeDisplay.value = "All"

            rankingList.value = getRankingList(marketId = currentMarketType.value ?: "TH", page = 0)

            onLoadingInitSuccess.call()
        }
    }

    private suspend fun getCountryList(): List<Country> {
        return getCountryListUseCase().let { response ->
            when (response) {
                is ApiResponse.Success -> response.data
                is ApiResponse.Failure -> emptyList()
            }
        }
    }

    private suspend fun getSectorTypeList(): List<Sector> {
        return getSectorListUseCase().let { response ->
            when (response) {
                is ApiResponse.Success -> response.data
                is ApiResponse.Failure -> emptyList()
            }
        }
    }

    private suspend fun getRankingList(marketId: String, page: Int): List<RankingItem> {
        val params = GetRankingListUseCase.Params(
            market = marketId,
            page = page
        )
        getRankingListUseCase.invoke(params)
        return getRankingListUseCase.invoke(params).let { response ->
            when (response) {
                is ApiResponse.Success -> response.data
                is ApiResponse.Failure -> emptyList()
            }
        }
    }

}