package com.example.myapplication.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.App
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 * Created by Samira Salah
 */
fun Context.bitmapFromUri(uri: Uri?): Bitmap? {
    if (uri == null) return null

    return try {
        if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(
                contentResolver, uri
            )
        } else {
            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    contentResolver, uri
                )
            )
        }
    } catch (ignored: Exception) {
        null
    }
}


fun Context.bitmapFromUrl(url: String): Bitmap? {
    val requestOptions = RequestOptions().override(100)
        .downsample(DownsampleStrategy.CENTER_INSIDE)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.NONE)

    try {
        return Glide.with(this)
            .asBitmap()
            .load(url)
            .apply(requestOptions)
            .submit()
            .get()
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    return null
}

class SaveFileAsyncTask(
    private var context: Context,
    private val url: String? = null,
    private val uri: Uri? = null,
    private val callback: (File) -> Unit
) : AsyncTask<Any, Int, File?>() {
    override fun doInBackground(vararg params: Any?): File? {
        try {
            val oldFile = File(PathAvatar)
            if (oldFile.exists()) oldFile.delete()
            val bitmap = if (url != null) context.bitmapFromUrl(url) else context.bitmapFromUri(uri)
            return bitmap?.bitmapToFile("avatar")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun onPostExecute(file: File?) {
        if (file != null) callback(file)
        super.onPostExecute(file)
    }
}

fun Bitmap.bitmapToFile(
    fileNameToSave: String
): File? { // File name like "image.png"
    //create a file to write bitmap data
    var file: File? = null
    return try {
        file =
            File(
                App.instance.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .toString() + "/" + fileNameToSave + ".jpeg"
            )
        file.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 50, bos) // YOU can also save it in JPEG
        val bitmapdata: ByteArray = bos.toByteArray()

        //write the bytes in file
        val fos = FileOutputStream(file)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()
        file
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        file // it will return null
    }
}

fun Bitmap?.resize(preferredWidth: Int, preferredHeight: Int): Bitmap? {
    if (this == null) return null
    if (preferredWidth <= 0 && preferredHeight <= 0) return this

    val bitmapRatio = width.toFloat() / height.toFloat()
    val preferredRatio = preferredWidth.toFloat() / preferredHeight.toFloat()

    var bitmapWidth = preferredWidth
    var bitmapHeight = preferredHeight
    if (preferredRatio > bitmapRatio) {
        bitmapWidth = (preferredHeight.toFloat() * bitmapRatio).toInt()
    } else {
        bitmapHeight = (preferredWidth.toFloat() / bitmapRatio).toInt()
    }
    return Bitmap.createScaledBitmap(this, bitmapWidth, bitmapHeight, true)
}

