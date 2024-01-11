package com.example.manajemenrs.repositori

import com.example.manajemenrs.data.Pasien
import kotlinx.coroutines.flow.Flow

interface PasienRepository {
    fun getAllPasienStream(): Flow<List<Pasien>>

    fun getPasienStream(id: Int): Flow<Pasien?>

    suspend fun insertPasien(pasien: Pasien)

    suspend fun deletePasien(pasien: Pasien)

    suspend fun updatePasien(pasien: Pasien)
}