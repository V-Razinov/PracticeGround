package ru.practiceground.other

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import ru.practiceground.App

fun <Binding : ViewDataBinding> getBinding(parent: ViewGroup?, viewId: Int): Binding =
    DataBindingUtil.inflate(LayoutInflater.from(parent?.context), viewId, parent, false)

fun getColor(@ColorRes id: Int): Int = ContextCompat.getColor(App.context, id)