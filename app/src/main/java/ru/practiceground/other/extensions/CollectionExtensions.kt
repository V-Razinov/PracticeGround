package ru.practiceground.other.extensions

fun <T>List<T>.multiple(times: Int): List<T> {
    val newCollection = mutableListOf<T>()
    repeat(times) { newCollection.addAll(this) }
    return newCollection
}