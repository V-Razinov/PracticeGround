package ru.practiceground.other

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <Binding : ViewDataBinding> getBinding(parent: ViewGroup?, viewId: Int): Binding =
    DataBindingUtil.inflate(LayoutInflater.from(parent?.context), viewId, parent, false)