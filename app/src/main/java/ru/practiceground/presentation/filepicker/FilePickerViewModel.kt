package ru.practiceground.presentation.filepicker

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import ru.practiceground.other.ShowLoader
import ru.practiceground.other.base.SingleLiveEvent
import ru.practiceground.other.extensions.int
import ru.practiceground.presentation.base.BaseViewModel
import java.io.File
import kotlin.math.round

class FilePickerViewModel : BaseViewModel() {

    val openFilePicker = SingleLiveEvent<Unit>()
    val name = MutableLiveData<String>()
    val size = MutableLiveData<String>()
    val fileUri = MutableLiveData<Uri?>()
    val openPickSizeDialog = SingleLiveEvent<Int>()
    var type = ""
    private var file: File? = null
    private var compressorJob: Job? = null
    private val fileLoader: FileLoader by lazy(LazyThreadSafetyMode.NONE, ::createFileLoader)

    fun onPickImageClick() {
        openFilePicker.value = Unit
    }

    fun onCompressClick() {
        openPickSizeDialog.value = file?.length()?.div(1000)?.int ?: return
    }

    fun onDialogOkClick(sizeTo: Int) {
        compressorJob?.cancel()
        file?.let { file ->
            compressorJob = viewModelScope.launch(Dispatchers.Main) {
                EventBus.getDefault().post(ShowLoader(true))
                withContext(Dispatchers.IO) {
                    ImageCompressor(file, sizeTo).execute {
                        withContext(Dispatchers.Main) { onFileCompressed(it) }
                    }
                }
                EventBus.getDefault().post(ShowLoader(false))
            }
        }
    }

    fun loadFile(uri: Uri, contentResolver: ContentResolver) {
        viewModelScope.launch(Dispatchers.IO) {
            fileLoader.load(uri, contentResolver, context.externalCacheDir ?: return@launch).let { loadedFile ->
                withContext(Dispatchers.Main) {
                    if (loadedFile == null) {
                        showMessage("error")
                    } else {
                        file = loadedFile
                        fileUri.value = Uri.fromFile(file)
                    }
                }
            }
        }
    }

    private fun createFileLoader(): FileLoader = FileLoader(object : FileLoader.IFileLoader {
        override suspend fun onNameLoaded(name: String?) = withContext(Dispatchers.Main) {
            this@FilePickerViewModel.name.value = name
        }

        override suspend fun onSizeLoaded(size: Long?) = withContext(Dispatchers.Main) {
            this@FilePickerViewModel.size.value = size?.configure ?: "-1"
        }

        override suspend fun onTypeLoaded(type: String?) = withContext(Dispatchers.Main) {
            this@FilePickerViewModel.type = type ?: ""
        }
    })

    private fun onFileCompressed(file: File?) {
        if (file == null) {
            showMessage("error")
        } else {
            this@FilePickerViewModel.file = file
            fileUri.value = Uri.fromFile(file)
            name.value = file.name
            size.value = file.length().configure
        }
    }

    private fun Double.round() = round(this)

    private val Number.configure: String
        get() = this.toDouble()
            .div(1000)
            .round()
            .toString()
            .plus("KB")
}