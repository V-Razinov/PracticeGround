package ru.practiceground.other

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import ru.practiceground.App
import ru.practiceground.R
import java.io.InputStream
import java.io.OutputStream

private const val COPY_STREAM_BYTE_ARRAY_SIZE = 1024 * 2
//Для Парент Фрагментов и итемов ресайкла
fun <Binding : ViewDataBinding> getBinding(parent: ViewGroup?, @LayoutRes layoutId: Int): Binding =
    DataBindingUtil.inflate(LayoutInflater.from(parent?.context), layoutId, parent, false)
//Для Чайлд фрагментов(диалоги, страницы viewpager)
fun <Binding : ViewDataBinding> getBinding(inflater: LayoutInflater, parent: ViewGroup?, @LayoutRes layoutId: Int): Binding =
    DataBindingUtil.inflate(inflater, layoutId, parent, false)

fun getView(parent: ViewGroup, @LayoutRes layoutId: Int): View =
    LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

fun getColor(@ColorRes id: Int): Int = ContextCompat.getColor(App.context, id)

fun getDialogPadding(context: Context): Int {
    val typedValue = TypedValue()
    return if (context.theme.resolveAttribute(R.attr.dialogPreferredPadding, typedValue, true))
        TypedValue.complexToDimensionPixelSize(typedValue.data, context.resources.displayMetrics)
    else
        0
}

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

inline fun <reified A, reified B : A> A.caster(): B? = if (B::class.java.isAssignableFrom(A::class.java))
    this as B
else
    null