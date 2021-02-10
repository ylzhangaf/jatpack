package com.ylozhangaf.libnetwork.cache

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ylozhangaf.libcommon.AppGlobals

@Database(entities = [Cache::class], version = 1, exportSchema = true)
abstract class CacheDatabase : RoomDatabase() {

    companion object{
        private val roomDataBase = Room.databaseBuilder(
            AppGlobals.getApplication(),
            CacheDatabase::class.java,
            "ylzhangaf_cache")
            .allowMainThreadQueries() // 是否允许在主线程进行查询
            .build()

        fun getDataBase() : CacheDatabase {
            return roomDataBase
        }
    }

    abstract fun getCache() : CacheDao
}