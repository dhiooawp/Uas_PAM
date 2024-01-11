package com.example.manajemenrs.repositori

import android.content.Context
import com.example.manajemenrs.data.RSDatabase

interface ContainerApp {
    val pasienRepository : PasienRepository
    val rekamMedisRepository : RekamMedisRepository
}

class ContainerDataApp(private val context: Context) : ContainerApp{
    override val pasienRepository: PasienRepository by lazy {
        OfflineRepositoryPasien(RSDatabase.getDatabase(context).pasienDao())
    }
    override val rekamMedisRepository: RekamMedisRepository by lazy {
        OfflineRepositoryRekamMedis(RSDatabase.getDatabase(context).rekamMedisDao())
    }
}