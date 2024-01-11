package com.example.manajemenrs.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pasien")
data class Pasien(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String = "",
    val alamat: String = "",
    val nohp: String = ""
)
