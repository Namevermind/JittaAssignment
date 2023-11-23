package android.jitta.assignment.ui.detail

import android.jitta.assignment.base.utils.ApiResponse
import android.jitta.assignment.data.entities.Detail
import android.jitta.assignment.domain.GetDetailByIdUseCase
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel(
    private val getDetailByIdUseCase: GetDetailByIdUseCase
) : ViewModel() {

    val detailItem = MutableLiveData<Detail?>()

    val titleLabel = MutableLiveData<String>()
    val idLabel = MutableLiveData<String>()
    val rankingDetailLabel = MutableLiveData<String>()
    val scoreLabel = MutableLiveData<String>()
    val currentDateLabel = MutableLiveData<String>()
    val currentPrice = MutableLiveData<String>()
    val valueDiffLabel = MutableLiveData<String>()
    val typeDiff = MutableLiveData<String>()
    val percentageDiffLabel = MutableLiveData<String>()
    val factorItem = MutableLiveData<Detail.Factor?>()
    val signListItem = MutableLiveData<List<Detail.Sign>>()
    val descriptionLabel = MutableLiveData<String>()
    val sectorLabel = MutableLiveData<String>()
    val industryLabel = MutableLiveData<String>()
    val website = MutableLiveData<String?>()

    fun initialize(itemId: String) {
        viewModelScope.launch {
            getDetailById(itemId)?.let { detail ->

                titleLabel.value = detail.title.orEmpty()
                idLabel.value = detail.id.orEmpty()
                rankingDetailLabel.value = buildString {
                    append("Jitta Ranking #")
                    append(detail.rank ?: 0)
                    append(" from ")
                    append(detail.member ?: 0)
                }
                scoreLabel.value = detail.score?.let { String.format("%.2f", it) } ?: "0.00"
                currentDateLabel.value = detail.currentDate?.let {
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
                    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)
                    val date: Date = inputFormat.parse(it)
                    val formattedDate = outputFormat.format(date)

                    "Price ($formattedDate)"
                }.orEmpty()
                currentPrice.value = buildString {
                    append("฿")
                    append(detail.currentPrice?.let { String.format("%.2f", it) } ?: "0.00")
                }
                valueDiffLabel.value = when ((detail.diff?.value ?: 0.00) > 0) {
                    true -> "Over Jitta Line"
                    else -> "Under Jitta Line"
                }
                typeDiff.value = detail.diff?.type.orEmpty()
                percentageDiffLabel.value = detail.diff?.percentage.orEmpty()
                factorItem.value = detail.factor
                signListItem.value = detail.sign.orEmpty()
                descriptionLabel.value = detail.description.orEmpty()
                sectorLabel.value = detail.sectorName.orEmpty()
                industryLabel.value = detail.industry.orEmpty()
                website.value = detail.website

                detailItem.value = detail
            }
        }
    }

    private suspend fun getDetailById(itemId: String): Detail? {
        val params = GetDetailByIdUseCase.Params(itemId)
        return getDetailByIdUseCase(params).let { response ->
            when (response) {
                is ApiResponse.Success -> response.data
                is ApiResponse.Failure -> null
            }
        }
    }
}