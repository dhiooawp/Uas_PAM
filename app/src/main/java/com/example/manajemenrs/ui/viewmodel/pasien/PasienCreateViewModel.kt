package com.example.manajemenrs.ui.viewmodel.pasien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.manajemenrs.data.Pasien
import com.example.manajemenrs.repositori.PasienRepository

class PasienCreateViewModel(private val pasienRepository: PasienRepository): ViewModel() {
    /**
     * Berisi Status Pasien Saat ini
     **/
    var UiStatePasien by mutableStateOf(UIStatePasien())
        private set

    /** Fungsi untuk memvalidasi input*/

    private fun validasiInput(uiState: DetailPasien = UiStatePasien.detailPasien): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && nohp.isNotBlank()
        }
    }

    fun updateUiState(detailPasien: DetailPasien){
        UiStatePasien =
            UIStatePasien(detailPasien = detailPasien, isEntryValid = validasiInput(detailPasien))
    }

    suspend fun savePasien() {
        if (validasiInput()) {
            pasienRepository.insertPasien(UiStatePasien.detailPasien.toPasien())
        }
    }
}
data class DetailPasien(
    val id: Int = 0,
    val nama: String = "",
    val alamat: String = "",
    val nohp: String = ""
)

data class UIStatePasien(
    val detailPasien: DetailPasien = DetailPasien(),
    val isEntryValid: Boolean = false
)

fun DetailPasien.toPasien(): Pasien = Pasien(
    id = id,
    nama = nama,
    alamat = alamat,
    nohp = nohp
)

fun Pasien.toUiStatePasien(isEntryValid: Boolean = false): UIStatePasien = UIStatePasien(
    detailPasien = this.toDetailPasien(),
    isEntryValid = isEntryValid
)

fun Pasien.toDetailPasien(): DetailPasien = DetailPasien(
    id = id,
    nama = nama,
    alamat = alamat,
    nohp = nohp
)