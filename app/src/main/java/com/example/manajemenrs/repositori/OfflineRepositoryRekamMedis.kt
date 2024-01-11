package com.example.manajemenrs.repositori

import com.example.manajemenrs.data.RekamMedis
import com.example.manajemenrs.data.RekamMedisDao
import kotlinx.coroutines.flow.Flow

class OfflineRepositoryRekamMedis(private val rekamMedisDao: RekamMedisDao): RekamMedisRepository {

    override fun getAllRekamMedisStream(): Flow<List<RekamMedis>> = rekamMedisDao.getAllRekamMedis()

    override fun getRekamMedisStream(id: Int): Flow<RekamMedis> = rekamMedisDao.getRekamMedis(id)

    override suspend fun insertRekamMedis(rekamMedis: RekamMedis) = rekamMedisDao.insert(rekamMedis)

    override suspend fun deleteRekamMedis(rekamMedis: RekamMedis) = rekamMedisDao.delete(rekamMedis)

    override suspend fun updateRekamMedis(rekamMedis: RekamMedis) = rekamMedisDao.update(rekamMedis)
}