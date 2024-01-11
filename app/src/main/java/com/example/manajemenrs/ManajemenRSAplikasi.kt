package com.example.manajemenrs

import android.app.Application
import com.example.manajemenrs.repositori.ContainerApp
import com.example.manajemenrs.repositori.ContainerDataApp

class ManajemenRSAplikasi : Application() {
    /**
     * App Container instance digunakan oleh kelas-kelas lainnya untuk mendapatkan dependensi
     */
    lateinit var container: ContainerApp

    override fun onCreate() {
        super.onCreate()
        container = ContainerDataApp(this)
    }
}