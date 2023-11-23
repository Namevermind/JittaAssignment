package android.jitta.assignment.data.entities

data class RankingItem(
    val id: String?,
    val symbol: String?,
    val title: String?,
    val rank: Int?,
    val count: Int?,
    val sectorId: String?
) {
    companion object {
        val emptyItem = RankingItem(
            id = "",
            symbol = "",
            title = "",
            rank = 0,
            count = 0,
            sectorId = ""
        )
    }
}