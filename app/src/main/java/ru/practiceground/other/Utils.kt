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