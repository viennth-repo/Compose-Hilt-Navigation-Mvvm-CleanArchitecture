package com.viennth.app.demo.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.viennth.app.demo.data.datasource.local.entity.SampleEntity
import javax.inject.Inject

@Database(entities = [SampleEntity::class], version = 1)
abstract class SampleRoomDb : RoomDatabase() {

    abstract fun sampleDao(): SampleDao

    companion object {
        @Volatile
        private var INSTANCE: SampleRoomDb? = null

        fun getDatabase(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context,
                SampleRoomDb::class.java,
                RoomDbConfig.DbName
            ).build().also { INSTANCE = it }
        }
    }
}