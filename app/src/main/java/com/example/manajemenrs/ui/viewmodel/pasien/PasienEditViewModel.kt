package com.example.manajemenrs.ui.viewmodel.pasien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manajemenrs.repositori.PasienRepository
import com.example.manajemenrs.ui.halaman.pasien.PasienEditDestination
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PasienEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val pasienRepository: PasienRepository
) : ViewModel() {

    var pasienUiState by mutableStateOf(UIStatePasien())
        private set

    private val itemId: Int = checkNotNull(savedStateHandle[PasienEditDestination.itemIdArg])

    init {
        viewModelScope.launch {
            pasienUiState = pasienRepository.getPasienStream(itemId)
                .filterNotNull()
                .first()
                .toUiStatePasien(true)
        }
    }

    suspend fun updatePasien() {
        if (validasiInput(pasienUiState.detailPasien)) {
            pasienRepository.updatePasien(pasienUiState.detailPasien.toPasien())
        }
        else {
            println("Data tidak valid")
        }
    }

    fun updateUiState(detailPasien: DetailPasien) {
        pasienUiState =
            UIStatePasien(detailPasien = detailPasien, isEntryValid = validasiInput(detailPasien))
    }

    private fun validasiInput(uiState: DetailPasien = pasienUiState.detailPasien ): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && nohp.isNotBlank()
        }
    }


}