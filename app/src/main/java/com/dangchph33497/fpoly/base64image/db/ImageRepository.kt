package com.dangchph33497.fpoly.base64image.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageRepository(private val imageDAO: ImageDAO) {

    suspend fun getAllImages(): List<ImageModel> = withContext(Dispatchers.IO) {
        imageDAO.getAllImages()
    }

    suspend fun insertNewImage(image: ImageModel) = withContext(Dispatchers.IO) {
        imageDAO.insertImage(image)
    }
}
