package ru.practiceground.other

import android.content.Context
import android.util.TypedValue
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import ru.practiceground.App
import ru.practiceground.R
import java.io.InputStream
import java.io.OutputStream

fun getColor(@ColorRes id: Int): Int = ContextCompat.getColor(App.context, id)
fun getDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(App.context, id)

fun getDialogPadding(context: Context): Int {
    val typedValue = TypedValue()
    return if (context.theme.resolveAttribute(R.attr.dialogPreferredPadding, typedValue, true)) {
        TypedValue.complexToDimensionPixelSize(typedValue.data, context.resources.displayMetrics)
    } else {
        0
    }
}

private const val COPY_STREAM_BYTE_ARRAY_SIZE = 1024 * 2
fun copyStream(inputStream: InputStream, outputStream: OutputStream) {
    val fileReader = ByteArray(COPY_STREAM_BYTE_ARRAY_SIZE)
    while (true) {
        val read: Int = inputStream.read(fileReader)
        if (read == -1) break
        outputStream.write(fileReader, 0, read)
    }
    outputStream.flush()
}

inline fun <reified T> Any?.castTo(): T? = this as? T