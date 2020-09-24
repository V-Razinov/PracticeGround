package ru.practiceground.presentation.filepicker

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import ru.practiceground.App
import ru.practiceground.other.ShowLoader
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class ImageCompressor(
    private val scope: CoroutineScope,
    private val file: File,
    private val sizeTo: Int
) {

    private val IMAGE_TYPE_PNG = "png"
    private val IMAGE_TYPE_JPG = "jpg"
    private val IMAGE_TYPE_WEBP = "webp"
    private var job: Job? = null

    fun execute(action: (File?) -> Unit, onProgress: (File) -> Unit) {
        job?.cancel()
        val postAction: (File?) -> Unit = { file: File? ->
            scope.launch(Dispatchers.Main) {
                EventBus.getDefault().post(ShowLoader(false))
                action.invoke(file)
                this@ImageCompressor.cancel()
            }
        }

        EventBus.getDefault().post(ShowLoader(true))
        job = scope.launch(Dispatchers.IO) {
            App.context.externalCacheDir?.delete()
            val fileType = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString())
            val cacheFile = File(App.context.externalCacheDir, "(compressed)${file.name}").apply {
                if (exists()) delete()
                file.copyTo(this)
            }
            if (cacheFile.length() > sizeTo) {
                try {
                    var streamLength = sizeTo
                    var compressQuality = 105
                    val bmpStream = ByteArrayOutputStream()
                    val compressFormat = when (fileType) {
                        IMAGE_TYPE_PNG -> Bitmap.CompressFormat.PNG
                        IMAGE_TYPE_JPG -> Bitmap.CompressFormat.JPEG
                        IMAGE_TYPE_WEBP -> Bitmap.CompressFormat.WEBP
                        else -> {
                            postAction.invoke(null)
                            return@launch
                        }
                    }
                    var prevSize = -1
                    while (streamLength >= sizeTo && compressQuality > 5) {
                        bmpStream.use {
                            it.flush()
                            it.reset()
                        }
                        compressQuality -= 5
                        val bitmap = BitmapFactory.decodeFile(cacheFile.absolutePath, BitmapFactory.Options())
                        bitmap.compress(compressFormat, compressQuality, bmpStream)
                        val bmpPicByteArray = bmpStream.toByteArray()
                        streamLength = bmpPicByteArray.size

                        Log.d("MyLogs", "sizeTo $sizeTo")
                        Log.d("MyLogs", "prevSize $prevSize")
                        Log.d("MyLogs", "streamLength $streamLength")
                        if (prevSize == streamLength) {
                            postAction.invoke(null)
                        }
                        prevSize = streamLength
                    }
                    FileOutputStream(cacheFile).use {
                        it.write(bmpStream.toByteArray())
                        it.flush()
                    }
                    postAction.invoke(cacheFile)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("MyLogs", "${e.message}")
                    postAction.invoke(null)
                }
            } else {
            }
        }
    }

    fun cancel() = job?.cancel()
}