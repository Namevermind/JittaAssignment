package android.jitta.assignment.ui.main.sectorContent

import android.jitta.assignment.R
import android.jitta.assignment.data.entities.Sector
import android.jitta.assignment.ui.main.sectorContent.adapter.SectorAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SectorDialogFragment(
    private val itemList: List<Sector>,
    private val currentItemId: String,
    private val onSectorSelected: (sectorId: String) -> Unit
) : DialogFragment() {

    private val adapter by lazy { SectorAdapter(::onSectorSelected) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_sector_content, container, false)
    }

    override fun getTheme() = R.style.Theme_JittaAssignment_DialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvSectorContent = view.findViewById<RecyclerView>(R.id.rv_sector_content)
        val btnClose = view.findViewById<ImageView>(R.id.btn_close_sector_dialog)

        btnClose.setOnClickListener {
            this.dismiss()
        }

        rvSectorContent.apply {
            adapter = this@SectorDialogFragment.adapter
            layoutManager = LinearLayoutManager(
                this@SectorDialogFragment.requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        adapter.setItem(itemList, currentItemId)

        rvSectorContent.smoothScrollToPosition(itemList.indexOfFirst { it.id == currentItemId })
    }


    private fun onSectorSelected(sectorId: String) {
        onSectorSelected.invoke(sectorId)
        this.dismiss()
    }

    companion object {
        const val TAG = "SectorDialogFragment"
    }
}