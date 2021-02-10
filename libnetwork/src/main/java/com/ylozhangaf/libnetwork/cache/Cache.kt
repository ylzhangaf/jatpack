package com.ylozhangaf.libnetwork.cache

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "cache")
data class Cache(@PrimaryKey val key : String) {

    var data : ByteArray? = null
}