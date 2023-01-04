package com.viennth.app.demo.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.viennth.app.demo.data.datasource.local.RoomDbConfig

@Entity(tableName = RoomDbConfig.DbName)
data class SampleEntity(
    @PrimaryKey
    @ColumnInfo(name = "_id") val id: String = "",
    @ColumnInfo(name = "_name") val name: String? = null
)
