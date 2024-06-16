package com.dangchph33497.fpoly.base64image.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageModel(
    @PrimaryKey(autoGenerate = true) var id : Int?,
    var imageString: String?
)