package com.example.manajemenrs.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.manajemenrs.ManajemenRSAplikasi
import com.example.manajemenrs.ui.viewmodel.pasien.PasienCreateViewModel
import com.example.manajemenrs.ui.viewmodel.pasien.PasienDetailViewModel
import com.example.manajemenrs.ui.viewmodel.pasien.PasienEditViewModel
import com.example.manajemenrs.ui.viewmodel.pasien.PasienHomeViewModel
import com.example.manajemenrs.ui.viewmodel.rekammedis.RekamMedisCreateViewModel
import com.example.manajemenrs.ui.viewmodel.rekammedis.RekamMedisDetailViewModel
import com.example.manajemenrs.ui.viewmodel.rekammedis.RekamMedisEditViewModel
import com.example.manajemenrs.ui.viewmodel.rekammedis.RekamMedisHomeViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            PasienHomeViewModel(aplikasiRS().container.pasienRepository)
        }
        initializer {
            PasienCreateViewModel(aplikasiRS().container.pasienRepository)
        }
        initializer {
            PasienDetailViewModel(
                createSavedStateHandle(),
                aplikasiRS().container.pasienRepository
            )
        }
        initializer {
            PasienEditViewModel(
                createSavedStateHandle(),
                aplikasiRS().container.pasienRepository
            )
        }

        initializer {
            RekamMedisHomeViewModel(aplikasiRS().container.rekamMedisRepository)
        }
        initializer {
            RekamMedisDetailViewModel(
                createSavedStateHandle(),
                aplikasiRS().container.rekamMedisRepository
            )
        }
        initializer {
            RekamMedisEditViewModel(
                createSavedStateHandle(),
                aplikasiRS().container.rekamMedisRepository
            )
        }
        initializer {
            RekamMedisCreateViewModel(aplikasiRS().container.rekamMedisRepository)
        }
    }
}

fun CreationExtras.aplikasiRS(): ManajemenRSAplikasi =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ManajemenRSAplikasi)