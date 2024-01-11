package com.example.manajemenrs.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RekamMedisDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(rekamMedis: RekamMedis)

    @Update
    suspend fun update(rekamMedis: RekamMedis)

    @Delete
    suspend fun delete(rekamMedis: RekamMedis)

    @Query("SELECT * from medis WHERE id = :id")
    fun getRekamMedis(id: Int): Flow<RekamMedis>

    @Query("SELECT * from medis ORDER BY nama ASC")
    fun getAllRekamMedis(): Flow<List<RekamMedis>>
}