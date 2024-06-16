package com.dangchph33497.fpoly.base64image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream


object BitMapConverter {
    fun convertBitMapToString(bitMap :Bitmap) : String{
        val baos = ByteArrayOutputStream()
        bitMap.compress(Bitmap.CompressFormat.JPEG,70,baos)
        val byteArray = baos.toByteArray()
        return Base64.encodeToString(byteArray,Base64.DEFAULT)
    }

    fun convertedStringToBitmap(encdedString : String) : Bitmap? {
        return try {
            val encodeByte = Base64.decode(encdedString,Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.size)

        }catch (e : Exception){
            e.printStackTrace()
            null
        }
    }
}
