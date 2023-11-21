package android.jitta.assignment.ui.splash

import android.jitta.assignment.base.utils.ApiResponse
import android.jitta.assignment.data.entities.Country
import android.jitta.assignment.data.entities.Sector
import android.jitta.assignment.domain.GetCountryListUseCase
import android.jitta.assignment.domain.GetSectorListUseCase
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(
    private val getCountryListUseCase: GetCountryListUseCase,
    private val getSectorListUseCase: GetSectorListUseCase
) : ViewModel() {

    val countryList = MutableLiveData<List<Country>>()
    val sectorList = MutableLiveData<List<Sector>>()

    fun initialize() {
        viewModelScope.launch {
            countryList.value = getCountryList()
            sectorList.value = getSectorTypeList()
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
        return return getSectorListUseCase().let { response ->
            when (response) {
                is ApiResponse.Success -> response.data
                is ApiResponse.Failure -> emptyList()
            }
        }
    }

}