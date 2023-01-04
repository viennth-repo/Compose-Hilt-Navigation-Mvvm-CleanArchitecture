package com.viennth.app.demo.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.viennth.app.demo.data.datasource.local.entity.SampleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SampleDao {

    @Query("SELECT * from ${RoomDbConfig.DbName}")
    fun getSamples(): Flow<List<SampleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSample(sampleEntities: List<SampleEntity>)
}