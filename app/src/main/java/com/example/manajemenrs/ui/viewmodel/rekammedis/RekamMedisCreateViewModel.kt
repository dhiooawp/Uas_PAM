package com.example.manajemenrs.ui.viewmodel.rekammedis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.manajemenrs.data.RekamMedis
import com.example.manajemenrs.repositori.RekamMedisRepository

class RekamMedisCreateViewModel(private val rekamMedisRepository: RekamMedisRepository): ViewModel() {
    /**
     * Berisi Status RekamMedis Saat ini
     **/
    var UiStateRekamMedis by mutableStateOf(UIStateRekamMedis())
        private set

    /** Fungsi untuk memvalidasi input*/

    private fun validasiInput(uiState: DetailRekamMedis = UiStateRekamMedis.detailRekamMedis): Boolean {
        return with(uiState) {
            nama.isNotBlank() && riwayat.isNotBlank() && resep.isNotBlank()
        }
    }

    fun updateUiState(detailRekamMedis: DetailRekamMedis){
        UiStateRekamMedis =
            UIStateRekamMedis(detailRekamMedis = detailRekamMedis, isEntryValid = validasiInput(detailRekamMedis))
    }

    suspend fun saveRekamMedis() {
        if (validasiInput()) {
            rekamMedisRepository.insertRekamMedis(UiStateRekamMedis.detailRekamMedis.toRekamMedis())
        }
    }
}
data class DetailRekamMedis(
    val id: Int = 0,
    val nama: String = "",
    val riwayat: String = "",
    val resep: String = ""
)

data class UIStateRekamMedis(
    val detailRekamMedis: DetailRekamMedis = DetailRekamMedis(),
    val isEntryValid: Boolean = false
)

fun DetailRekamMedis.toRekamMedis(): RekamMedis = RekamMedis(
    id = id,
    nama = nama,
    riwayat = riwayat,
    resep = resep
)

fun RekamMedis.toUiStateRekamMedis(isEntryValid: Boolean = false): UIStateRekamMedis = UIStateRekamMedis(
    detailRekamMedis = this.toDetailRekamMedis(),
    isEntryValid = isEntryValid
)

fun RekamMedis.toDetailRekamMedis(): DetailRekamMedis = DetailRekamMedis(
    id = id,
    nama = nama,
    riwayat = riwayat,
    resep = resep
)