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

    var isHasMoreRankingItem = true
    var currentMarketType = "TH"
    var currentSectorId = ""
    private var currentPage = 0

    val countryList = MutableLiveData<List<Country>>()
    val sectorList = MutableLiveData<List<Sector>>()
    val rankingList = MutableLiveData<List<RankingItem>>()

    val onLoadingInitSuccess = MutableLiveEvent<Unit>()

    val marketTypeDisplay = MutableLiveData<String>()
    val sectorTypeDisplay = MutableLiveData<String>()

    fun initialize() {
        currentPage = 0
        viewModelScope.launch {
            countryList.value = getCountryList()
            sectorList.value = getSectorTypeList()

            // setup init value
            countryList.value?.find { it.code == "TH" }?.also {
                marketTypeDisplay.value = it.name.orEmpty()
                currentMarketType = it.code.orEmpty()
            }

            sectorList.value?.firstOrNull()?.also {
                sectorTypeDisplay.value = it.name.orEmpty()
                currentSectorId = it.id.orEmpty()
            }

            val newRankingItems =
                getRankingList(
                    marketId = currentMarketType,
                    sectorId = currentSectorId,
                    page = currentPage
                )
            updateRankList(newRankingItems)

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
                is ApiResponse.Success -> {
                    val newList = mutableListOf<Sector>()
                    newList.add(Sector("", "All sector")) // Add All sector
                    newList.addAll(response.data)
                    return newList
                }

                is ApiResponse.Failure -> emptyList()
            }
        }
    }

    private suspend fun getRankingList(
        marketId: String,
        sectorId: String,
        page: Int
    ): List<RankingItem> {
        val params = GetRankingListUseCase.Params(
            market = marketId,
            sectorId = sectorId,
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

    fun loadMoreRankingList() {
        currentPage++
        viewModelScope.launch {
            val newRankingItems = getRankingList(
                marketId = currentMarketType,
                sectorId = currentSectorId,
                page = currentPage
            )
            updateRankList(newRankingItems)
        }
    }

    suspend fun refresh() {
        resetPage()
        val newRankingItems = getRankingList(
            marketId = currentMarketType,
            sectorId = currentSectorId,
            page = currentPage
        )
        isHasMoreRankingItem = newRankingItems.size >= 30
        rankingList.value = newRankingItems
    }

    fun changeMarketRankingList(marketCode: String, sectorId: String) {
        currentMarketType = marketCode
        currentSectorId = sectorId
        marketTypeDisplay.value = countryList.value?.find { it.code == marketCode }?.name.orEmpty()
        sectorTypeDisplay.value = sectorList.value?.find { it.id == sectorId }?.name.orEmpty()

        viewModelScope.launch {
            resetPage()
            rankingList.value = listOf()
            val newRankingItems = getRankingList(
                marketId = currentMarketType,
                sectorId = currentSectorId,
                page = currentPage
            )
            updateRankList(newRankingItems)
        }

    }

    private fun resetPage() {
        isHasMoreRankingItem = true
        currentPage = 0
    }

    private fun updateRankList(newRankingItems: List<RankingItem>) {
        isHasMoreRankingItem = newRankingItems.size >= 30
        val updateList = mutableListOf<RankingItem>()
        rankingList.value?.let { updateList.addAll(it) }
        updateList.addAll(newRankingItems)
        rankingList.value = updateList
    }
}