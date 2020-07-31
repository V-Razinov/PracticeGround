package ru.practiceground.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import ru.practiceground.App

fun <Binding : ViewDataBinding> getBinding(parent: ViewGroup?, @LayoutRes layoutId: Int): Binding =
    DataBindingUtil.inflate(LayoutInflater.from(parent?.context), layoutId, parent, false)

fun getView(parent: ViewGroup, @LayoutRes layoutId: Int): View =
    LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

fun getColor(@ColorRes id: Int): Int = ContextCompat.getColor(App.context, id)