import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dangchph33497.fpoly.base64image.db.ImageModel
import com.dangchph33497.fpoly.base64image.db.ImageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ImageViewModel(private val repository: ImageRepository) : ViewModel() {

    private val _images = MutableStateFlow<List<ImageModel>>(emptyList())
    val images: StateFlow<List<ImageModel>> = _images.asStateFlow()

    init {
        fetchAllImages()
    }

    private fun fetchAllImages() {
        viewModelScope.launch {
            _images.value = repository.getAllImages()
        }
    }

    fun addImage(imageModel: ImageModel) {
        viewModelScope.launch {
            repository.insertNewImage(imageModel)
            fetchAllImages()
        }
    }
}
