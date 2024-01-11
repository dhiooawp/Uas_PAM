package com.example.manajemenrs.repositori

import com.example.manajemenrs.data.RekamMedis
import kotlinx.coroutines.flow.Flow

interface RekamMedisRepository {
    fun getAllRekamMedisStream(): Flow<List<RekamMedis>>

    fun getRekamMedisStream(id: Int): Flow<RekamMedis?>

    suspend fun insertRekamMedis(rekamMedis: RekamMedis)

    suspend fun deleteRekamMedis(rekamMedis: RekamMedis)

    suspend fun updateRekamMedis(rekamMedis: RekamMedis)
}