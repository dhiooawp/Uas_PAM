package com.example.manajemenrs.repositori

import com.example.manajemenrs.data.Pasien
import com.example.manajemenrs.data.PasienDao
import kotlinx.coroutines.flow.Flow

class OfflineRepositoryPasien(private val pasienDao: PasienDao): PasienRepository {

    override fun getAllPasienStream(): Flow<List<Pasien>> = pasienDao.getAllPasien()

    override fun getPasienStream(id: Int): Flow<Pasien> = pasienDao.getPasien(id)

    override suspend fun insertPasien(pasien: Pasien) = pasienDao.insert(pasien)

    override suspend fun deletePasien(pasien: Pasien) = pasienDao.delete(pasien)

    override suspend fun updatePasien(pasien: Pasien) = pasienDao.update(pasien)
}