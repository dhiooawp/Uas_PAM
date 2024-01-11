package com.example.manajemenrs.ui.viewmodel.rekammedis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manajemenrs.repositori.RekamMedisRepository
import com.example.manajemenrs.ui.halaman.rekammedis.RekamMedisEditDestination
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RekamMedisEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val rekamMedisRepository: RekamMedisRepository
) : ViewModel() {

    var rekamMedisUiState by mutableStateOf(UIStateRekamMedis())
        private set

    private val itemId: Int = checkNotNull(savedStateHandle[RekamMedisEditDestination.itemIdArg])

    init {
        viewModelScope.launch {
            rekamMedisUiState = rekamMedisRepository.getRekamMedisStream(itemId)
                .filterNotNull()
                .first()
                .toUiStateRekamMedis(true)
        }
    }

    suspend fun updateRekamMedis() {
        if (validasiInput(rekamMedisUiState.detailRekamMedis)) {
            rekamMedisRepository.updateRekamMedis(rekamMedisUiState.detailRekamMedis.toRekamMedis())
        }
        else {
            println("Data tidak valid")
        }
    }

    fun updateUiState(detailRekamMedis: DetailRekamMedis) {
        rekamMedisUiState =
            UIStateRekamMedis(detailRekamMedis = detailRekamMedis, isEntryValid = validasiInput(detailRekamMedis))
    }

    private fun validasiInput(uiState: DetailRekamMedis = rekamMedisUiState.detailRekamMedis ): Boolean {
        return with(uiState) {
            nama.isNotBlank() && resep.isNotBlank() && riwayat.isNotBlank()
        }
    }


}