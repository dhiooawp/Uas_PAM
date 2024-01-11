package com.example.manajemenrs.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PasienDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pasien: Pasien)

    @Update
    suspend fun update(pasien: Pasien)

    @Delete
    suspend fun delete(pasien: Pasien)

    @Query("SELECT * from pasien WHERE id = :id")
    fun getPasien(id: Int): Flow<Pasien>

    @Query("SELECT * from pasien ORDER BY nama ASC")
    fun getAllPasien(): Flow<List<Pasien>>
}