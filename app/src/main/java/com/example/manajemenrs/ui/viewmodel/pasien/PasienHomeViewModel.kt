package com.example.manajemenrs.ui.viewmodel.pasien

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manajemenrs.data.Pasien
import com.example.manajemenrs.repositori.PasienRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PasienHomeViewModel(private val pasienRepository: PasienRepository) : ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val homeUiState: StateFlow<HomeUiState> = pasienRepository.getAllPasienStream()
        .filterNotNull()
        .map { HomeUiState(listPasien = it.toList()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )
}

data class HomeUiState(
    val listPasien: List<Pasien> = listOf()
)