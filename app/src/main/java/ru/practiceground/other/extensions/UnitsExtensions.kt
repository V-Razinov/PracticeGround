package ru.practiceground.other.extensions

import android.util.TypedValue
import ru.practiceground.App

fun Int.toDp(): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    App.context.resources.displayMetrics
).toInt()

fun Float.toDp(): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    App.context.resources.displayMetrics
).toInt()