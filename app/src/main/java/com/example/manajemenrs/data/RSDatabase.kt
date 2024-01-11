package com.example.manajemenrs.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Pasien::class, RekamMedis::class], version = 1, exportSchema = false)
abstract class RSDatabase : RoomDatabase(){
    abstract fun pasienDao() : PasienDao
    abstract fun rekamMedisDao() : RekamMedisDao

    companion object{
        @Volatile
        private var Instance: RSDatabase? = null

        fun getDatabase(context: Context): RSDatabase{
            return (Instance?: synchronized(this){
                Room.databaseBuilder(context,RSDatabase::class.java,"rs_database").build().also{ Instance = it}
            })
        }
    }
}