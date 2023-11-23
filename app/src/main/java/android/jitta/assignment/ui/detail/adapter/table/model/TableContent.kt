package android.jitta.assignment.ui.detail.adapter.table.model

sealed class TableContent(
    open val listOfLabel: List<String>,
    open val type: DisplayType
) {

    data class Head(
        val heads: List<String>
    ) : TableContent(heads, DisplayType.COLUMN_HEAD)

    data class Column(
        val columns: List<String>
    ) : TableContent(columns, DisplayType.COLUMN)

    enum class DisplayType(val viewType: Int) {
        COLUMN_HEAD(0), COLUMN(1)
    }


}
