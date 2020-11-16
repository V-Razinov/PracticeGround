package ru.practiceground.presentation.filepicker

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import ru.practiceground.App
import ru.practiceground.other.ShowLoader
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class ImageCompressor(private val file: File, private val sizeTo: Int) {

    private val IMAGE_TYPE_PNG = "png"
    private val IMAGE_TYPE_JPG = "jpg"
    private val IMAGE_TYPE_WEBP = "webp"

    private val bitMapFactoryOptions by lazy (BitmapFactory::Options)
    private val cacheDirectory = App.context.externalCacheDir
    private val uri: Uri get() = Uri.fromFile(file)

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun execute(action: suspend (File?) -> Unit) {
        cacheDirectory?.delete()
        val cacheFile = createCacheFile()
        val compressFormat = getCompressFormat() ?: kotlin.run {
            action(null)
            return
        }
        val bmpStream = ByteArrayOutputStream()
        if (cacheFile.length() > sizeTo) {
            try {
                var streamLength = sizeTo
                var compressQuality = 105
                var prevSize = -1
                while (streamLength >= sizeTo && compressQuality > 5) {
                    bmpStream.run { flush(); reset() }
                    compressQuality -= 5

                    val bitmap = BitmapFactory.decodeFile(cacheFile.absolutePath, bitMapFactoryOptions)
                    bitmap.compress(compressFormat, compressQuality, bmpStream)

                    streamLength =  bmpStream.toByteArray().size
                    if (prevSize == streamLength) {
                        action(null)
                    }
                    prevSize = streamLength
                }
                FileOutputStream(cacheFile).run { write(bmpStream.toByteArray()); flush(); close() }
                action(cacheFile)
            } catch (e: Exception) {
                e.printStackTrace()
                action(null)
            } finally {
                bmpStream.close()
            }
        } else {
            action(null)
        }
    }

    private fun createCacheFile() = File(cacheDirectory, "(compressed)${file.name}").apply {
        if (exists()) {
            delete()
        }
        file.copyTo(this)
    }

    private fun getCompressFormat() = when (MimeTypeMap.getFileExtensionFromUrl(uri.toString())) {
        IMAGE_TYPE_PNG -> Bitmap.CompressFormat.PNG
        IMAGE_TYPE_JPG -> Bitmap.CompressFormat.JPEG
        IMAGE_TYPE_WEBP -> Bitmap.CompressFormat.WEBP
        else -> null
    }
}