package com.dangchph33497.fpoly.base64image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.dangchph33497.fpoly.base64image.db.ImageDB
import com.dangchph33497.fpoly.base64image.db.ImageRepository
import com.dangchph33497.fpoly.base64image.db.ImageModel
import com.dangchph33497.fpoly.base64image.ui.theme.Base64ImageTheme
import java.io.InputStream

class MainActivity : ComponentActivity() {
    private lateinit var imageViewModel: ImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = ImageDB.getInstance(applicationContext)
        val repository = ImageRepository(database.imageDAO)

        imageViewModel = ViewModelProvider(
            this,
            ImageViewModelFactory(repository)
        ).get(ImageViewModel::class.java)

        val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val bitmap = uriToBitmap(it)
                bitmap?.let { bmp ->
                    val resizedBitmap = ImageResizer.resizeBitmap(bmp, maxWidth = 800, maxHeight = 800)
                    val imageString = BitMapConverter.convertBitMapToString(resizedBitmap)
                    val newImage = ImageModel(id = null, imageString = imageString)
                    imageViewModel.addImage(newImage)
                }
            }
        }

        setContent {
            Base64ImageTheme {
                ImageScreen(viewModel = imageViewModel, onAddImageClick = {
                    pickImageLauncher.launch("image/*")
                })
            }
        }
    }

    private fun uriToBitmap(uri: Uri): Bitmap? {
        return try {
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
@Composable
fun ImageScreen(viewModel: ImageViewModel, onAddImageClick: () -> Unit) {
    val images by viewModel.images.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddImageClick) {
                Text("+")
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(images) { imageModel ->
                val bitmap = BitMapConverter.convertedStringToBitmap(imageModel.imageString ?: "")
                bitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.size(128.dp)
                    )
                }
            }
        }
    }
}

