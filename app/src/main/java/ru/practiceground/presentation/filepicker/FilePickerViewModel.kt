package ru.practiceground.presentation.filepicker

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practiceground.other.base.SingleLiveEvent
import ru.practiceground.other.copyStream
import ru.practiceground.presentation.base.BaseViewModel
import java.io.File
import java.io.FileOutputStream
import kotlin.math.round

class FilePickerViewModel : BaseViewModel() {

    val openFilePicker = SingleLiveEvent<Boolean>()
    val name = MutableLiveData<String>()
    val size = MutableLiveData<String>()
    val fileUri = MutableLiveData<Uri?>()
    val openPickSizeDialog = SingleLiveEvent<Int>()
    var type = ""
    private var imageCompressor: ImageCompressor? = null
    private var file: File? = null

    fun onPickImageClick() {
        openFilePicker.value = true
    }

    fun onCompressClick() {
        openPickSizeDialog.value = file?.length()?.div(1000)?.toInt() ?: return
    }

    fun onDialogOkClick(sizeTo: Int) {
        imageCompressor?.cancel()
        imageCompressor = ImageCompressor(viewModelScope, file ?: return, sizeTo).apply {
            execute(
                action = {
                    if (it == null) {
                        showMessage("error")
                    } else {
                        file = it
                        fileUri.value = Uri.fromFile(it)
                        name.value = it.name
                        size.value = it.length()
                            .toDouble()
                            .div(1000)
                            .apply { round(this) }
                            .toString()
                            .plus("KB")
                    }
                },
                onProgress = {
                    file = it
                    fileUri.value = Uri.fromFile(it)
                    name.value = it.name
                    size.value = it.length()
                        .toDouble()
                        .div(1000)
                        .apply { round(this) }
                        .toString()
                        .plus("KB")
                }
            )
        }
    }

    fun loadFile(uri: Uri, contentResolver: ContentResolver) {
        viewModelScope.launch(Dispatchers.IO) {
            contentResolver.query(uri, null, null, null, null).use { cursor ->
                if (cursor?.moveToFirst() == true) {
                    name.postValue(cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)))
                    val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                    if (!cursor.isNull(sizeIndex))
                        size.postValue(
                            contentResolver.openFileDescriptor(uri, "r")?.statSize
                                ?.toDouble()
                                ?.div(1000)
                                ?.apply { round(this) }
                                ?.toString()
                                ?.plus("KB")
                        )
                } else {
                    showMessage("Error")
                }
            }
            type = contentResolver.getType(uri) ?: ""

            contentResolver.openInputStream(uri).use { inputStream ->
                inputStream ?: return@launch
                fileUri.postValue(try {
                    file?.delete()
                    file = File(context.externalCacheDir, name.value ?: "File").apply { createNewFile() }
                    val fileOutputStream = FileOutputStream(file)
                    copyStream(inputStream, fileOutputStream)
                    fileOutputStream.flush()
                    Uri.fromFile(file)
                } catch (e: Exception) {
                    null
                })
            }
        }
    }
}