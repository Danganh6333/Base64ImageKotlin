package com.dangchph33497.fpoly.base64image.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ImageModel::class], version = 1, exportSchema = false)
abstract class ImageDB : RoomDatabase() {
    abstract val imageDAO: ImageDAO

    companion object {
        @Volatile
        private var INSTANCE: ImageDB? = null

        fun getInstance(context: Context): ImageDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ImageDB::class.java,
                    "imageDb"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
