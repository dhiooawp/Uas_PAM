package com.example.manajemenrs.ui.viewmodel.rekammedis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manajemenrs.data.RekamMedis
import com.example.manajemenrs.repositori.RekamMedisRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RekamMedisHomeViewModel (private val rekamMedisRepository: RekamMedisRepository) : ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val homeUiState: StateFlow<HomeUiState> = rekamMedisRepository.getAllRekamMedisStream()
        .filterNotNull()
        .map { HomeUiState(listRekamMedis = it.toList()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )
}

data class HomeUiState(
    val listRekamMedis: List<RekamMedis> = listOf()
)