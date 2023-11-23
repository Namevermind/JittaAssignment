package android.jitta.assignment.data.entities

import android.jitta.assignment.type.FactorLevel

data class Detail(
    val title: String?,
    val id: String?,
    val rank: Int?,
    val member: Int?,
    val score: Double?,
    val currentDate: String?,
    val currencySign: String?,
    val currentPrice: Double?,
    val diff: Diff?,
    val factor: Factor?,
    val sign: List<Sign>?,
    val description: String?,
    val sectorName: String?,
    val industry: String?,
    val website: String?
) {

    data class Diff(
        val value: Double?,
        val type: String?,
        val percentage: String?
    )

    data class Factor(
        val growthItem: Item?,
        val recentItem: Item?,
        val financialItem: Item?,
        val returnItem: Item?,
        val managementItem: Item?
    ) {
        data class Item(
            val name: String?,
            val level: FactorLevel?,
            val value: Double?
        )
    }

    data class Sign(
        val title: String?,
        val type: String?,
        val value: String?,
        val display: Display
    ) {
        data class Display(
            val columnHead: List<String>?,
            val columns: List<Columns>?
        )

        data class Columns(
            val name: String?,
            val data: List<String>?
        )
    }
}