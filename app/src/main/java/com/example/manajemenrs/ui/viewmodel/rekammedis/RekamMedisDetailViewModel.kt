package com.example.manajemenrs.ui.viewmodel.rekammedis

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manajemenrs.repositori.RekamMedisRepository
import com.example.manajemenrs.ui.halaman.rekammedis.DetailsDestinationRekamMedis
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RekamMedisDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val rekamMedisRepository: RekamMedisRepository
) : ViewModel() {

    private val medisId: Int = checkNotNull(savedStateHandle[DetailsDestinationRekamMedis.rekamMedisIdArg])
    val uiState: StateFlow<RekamMedisDetaislUiState> =
        rekamMedisRepository.getRekamMedisStream(medisId).filterNotNull()
            .map { RekamMedisDetaislUiState(detailRekamMedis = it.toDetailRekamMedis()) }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = RekamMedisDetaislUiState()
            )

    suspend fun deleteItem() {
        rekamMedisRepository.deleteRekamMedis(uiState.value.detailRekamMedis.toRekamMedis())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class RekamMedisDetaislUiState(
    val outOfStock: Boolean = true,
    val detailRekamMedis: DetailRekamMedis = DetailRekamMedis()
)