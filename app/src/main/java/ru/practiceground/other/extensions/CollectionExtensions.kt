package ru.practiceground.other.extensions

import kotlin.math.ceil

fun <T>List<T>.multiple(times: Int): List<T> {
    val newCollection = ArrayList<T>(size * times)
    repeat(times) { newCollection.addAll(this) }
    return newCollection
}

fun <T> List<T>.splitBySize(count: Int): List<List<T>> {
    if (count >= size) {
        return listOf(this)
    }

    val splitted = mutableListOf<List<T>>()
    repeat(ceil(size.toDouble().div(count)).int) { iteration ->
        val fromIndex = iteration * count
        val endIndex = if (fromIndex + count > size) size else fromIndex + count
        splitted.add(subList(fromIndex, endIndex))
    }
    return splitted
}

fun <T>MutableList<T>.replaceAll(newList: Collection<T>) {
    clear()
    addAll(newList)
}