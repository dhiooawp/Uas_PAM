package com.example.manajemenrs.ui.viewmodel.pasien

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manajemenrs.repositori.PasienRepository
import com.example.manajemenrs.ui.halaman.pasien.DetailsDestinationPasien
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PasienDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val pasienRepository: PasienRepository
) : ViewModel() {

    private val pasienId: Int = checkNotNull(savedStateHandle[DetailsDestinationPasien.pasienIdArg])
    val uiState: StateFlow<PasienDetaislUiState> =
        pasienRepository.getPasienStream(pasienId).filterNotNull()
            .map { PasienDetaislUiState(detailPasien = it.toDetailPasien()) }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = PasienDetaislUiState()
            )

    suspend fun deleteItem() {
        pasienRepository.deletePasien(uiState.value.detailPasien.toPasien())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class PasienDetaislUiState(
    val outOfStock: Boolean = true,
    val detailPasien: DetailPasien = DetailPasien()
)