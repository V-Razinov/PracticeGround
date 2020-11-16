package ru.practiceground.presentation.filepicker

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import ru.practiceground.other.copyStream
import java.io.File
import java.io.FileOutputStream

class FileLoader(private var listener: IFileLoader) {

    fun setListener(loaderListener: IFileLoader) {
        listener = loaderListener
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun load(uri: Uri, contentResolver: ContentResolver, cacheDir: File): File? {
        var fileName: String
        contentResolver.query(uri, null, null, null, null).use { cursor ->
            if (cursor?.moveToFirst() != true) {
                return null
            }
            fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            listener.onNameLoaded(fileName)

            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            if (!cursor.isNull(sizeIndex)) {
                try {
                    contentResolver.openFileDescriptor(uri, "r")?.statSize
                } catch (e: Exception) {
                    null
                }
            } else {
                null
            }.let { listener.onSizeLoaded(it) }
        }
        listener.onTypeLoaded(contentResolver.getType(uri))

        return try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                val file = File(cacheDir, fileName).apply(File::createNewFile)
                val fileOutputStream = FileOutputStream(file)
                copyStream(inputStream, fileOutputStream)
                fileOutputStream.close()
                file
            }
        } catch (e: Exception) {
            null
        }
    }

    interface IFileLoader {
        suspend fun onNameLoaded(name: String?)
        suspend fun onSizeLoaded(size: Long?)
        suspend fun onTypeLoaded(type: String?)
    }
}