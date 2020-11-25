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

val Int.dp get() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    App.context.resources.displayMetrics
)

val Float.dp get() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    App.context.resources.displayMetrics
)

val Number.float get() = if (this is Float) this else this.toFloat()
val Number.int get() = if (this is Int) this else this.toInt()
val Any.string get() = this.toString()