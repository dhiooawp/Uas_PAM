package com.example.manajemenrs.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medis")
data class RekamMedis(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String = "",
    val riwayat: String = "",
    val resep: String = ""
)
