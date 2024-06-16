import android.graphics.Bitmap

object ImageResizer {
    fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        var width = bitmap.width
        var height = bitmap.height

        if (width > maxWidth || height > maxHeight) {
            val ratioBitmap = width.toFloat() / height.toFloat()
            val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()

            if (ratioBitmap > ratioMax) {
                width = maxWidth
                height = (width / ratioBitmap).toInt()
            } else {
                height = maxHeight
                width = (height * ratioBitmap).toInt()
            }
        }

        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }
}
