package com.dangchph33497.fpoly.base64image.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDAO {
    @Query("SELECT * FROM ImageModel")
    suspend fun getAllImages() : List<ImageModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(imageModel: ImageModel)
}